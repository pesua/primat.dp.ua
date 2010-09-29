/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.portal.service.impl;

import com.liferay.util.mail.MailEngine;
import com.liferay.portal.ContactBirthdayException;
import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.ContactFullNameException;
import com.liferay.portal.ContactLastNameException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.DuplicateUserScreenNameException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.ModelListenerException;
import com.liferay.portal.NoSuchContactException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.PasswordExpiredException;
import com.liferay.portal.RequiredUserException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.ReservedUserScreenNameException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserLockoutException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserPortraitSizeException;
import com.liferay.portal.UserPortraitTypeException;
import com.liferay.portal.UserReminderQueryException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.UserSmsException;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.security.auth.AuthPipeline;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.security.auth.EmailAddressGenerator;
import com.liferay.portal.security.auth.EmailAddressGeneratorFactory;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.security.auth.FullNameValidator;
import com.liferay.portal.security.auth.FullNameValidatorFactory;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.security.auth.ScreenNameValidator;
import com.liferay.portal.security.auth.ScreenNameValidatorFactory;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.security.pwd.PwdToolkitUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.base.UserLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.enterpriseadmin.util.EnterpriseAdminUtil;
import com.liferay.portlet.imagegallery.ImageSizeException;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.awt.image.RenderedImage;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.internet.InternetAddress;

/**
 * <a href="UserLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Julio Camarero
 * @author Wesley Gong
 * @author Zsigmond Rab
 */ 
public class UserLocalServiceImpl extends UserLocalServiceBaseImpl {

    public void addDefaultGroups(long userId)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        Set<Long> groupIdsSet = new HashSet<Long>();

        String[] defaultGroupNames = PrefsPropsUtil.getStringArray(
                user.getCompanyId(), PropsKeys.ADMIN_DEFAULT_GROUP_NAMES,
                StringPool.NEW_LINE, PropsValues.ADMIN_DEFAULT_GROUP_NAMES);

        for (String defaultGroupName : defaultGroupNames) {
            try {
                Group group = groupPersistence.findByC_N(
                        user.getCompanyId(), defaultGroupName);

                if (!userPersistence.containsGroup(
                        userId, group.getGroupId())) {

                    groupIdsSet.add(group.getGroupId());
                }
            } catch (NoSuchGroupException nsge) {
            }
        }

        long[] groupIds = ArrayUtil.toArray(
                groupIdsSet.toArray(new Long[groupIdsSet.size()]));

        groupLocalService.addUserGroups(userId, groupIds);
    }

    public void addDefaultRoles(long userId)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        Set<Long> roleIdSet = new HashSet<Long>();

        String[] defaultRoleNames = PrefsPropsUtil.getStringArray(
                user.getCompanyId(), PropsKeys.ADMIN_DEFAULT_ROLE_NAMES,
                StringPool.NEW_LINE, PropsValues.ADMIN_DEFAULT_ROLE_NAMES);

        for (String defaultRoleName : defaultRoleNames) {
            try {
                Role role = rolePersistence.findByC_N(
                        user.getCompanyId(), defaultRoleName);

                if (!userPersistence.containsRole(
                        userId, role.getRoleId())) {

                    roleIdSet.add(role.getRoleId());
                }
            } catch (NoSuchRoleException nsre) {
            }
        }

        long[] roleIds = ArrayUtil.toArray(
                roleIdSet.toArray(new Long[roleIdSet.size()]));

        roleIds = EnterpriseAdminUtil.addRequiredRoles(user, roleIds);

        userPersistence.addRoles(userId, roleIds);
    }

    public void addDefaultUserGroups(long userId)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        Set<Long> userGroupIdSet = new HashSet<Long>();

        String[] defaultUserGroupNames = PrefsPropsUtil.getStringArray(
                user.getCompanyId(), PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES,
                StringPool.NEW_LINE, PropsValues.ADMIN_DEFAULT_USER_GROUP_NAMES);

        for (String defaultUserGroupName : defaultUserGroupNames) {
            try {
                UserGroup userGroup = userGroupPersistence.findByC_N(
                        user.getCompanyId(), defaultUserGroupName);

                if (!userPersistence.containsUserGroup(
                        userId, userGroup.getUserGroupId())) {

                    userGroupIdSet.add(userGroup.getUserGroupId());
                }
            } catch (NoSuchUserGroupException nsuge) {
            }
        }

        long[] userGroupIds = ArrayUtil.toArray(
                userGroupIdSet.toArray(new Long[userGroupIdSet.size()]));

        for (long userGroupId : userGroupIds) {
            userGroupLocalService.copyUserGroupLayouts(userGroupId, userId);
        }

        userPersistence.addUserGroups(userId, userGroupIds);
    }

    public void addGroupUsers(long groupId, long[] userIds)
            throws PortalException, SystemException {

        groupPersistence.addUsers(groupId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public void addOrganizationUsers(long organizationId, long[] userIds)
            throws PortalException, SystemException {

        organizationPersistence.addUsers(organizationId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
            throws SystemException {

        passwordPolicyRelLocalService.addPasswordPolicyRels(
                passwordPolicyId, User.class.getName(), userIds);
    }

    public void addRoleUsers(long roleId, long[] userIds)
            throws PortalException, SystemException {

        rolePersistence.addUsers(roleId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public void addTeamUsers(long teamId, long[] userIds)
            throws PortalException, SystemException {

        teamPersistence.addUsers(teamId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public User addUser(
            long creatorUserId, long companyId, boolean autoPassword,
            String password1, String password2, boolean autoScreenName,
            String screenName, String emailAddress, String openId,
            Locale locale, String firstName, String middleName, String lastName,
            int prefixId, int suffixId, boolean male, int birthdayMonth,
            int birthdayDay, int birthdayYear, String jobTitle, long[] groupIds,
            long[] organizationIds, long[] roleIds, long[] userGroupIds,
            boolean sendEmail, ServiceContext serviceContext)
            throws PortalException, SystemException {

        ServiceContext previousServiceContext =
                ServiceContextThreadLocal.getServiceContext();

        ServiceContextThreadLocal.setServiceContext(serviceContext);

        try {
            return doAddUser(
                    creatorUserId, companyId, autoPassword, password1, password2,
                    autoScreenName, screenName, emailAddress, openId, locale,
                    firstName, middleName, lastName, prefixId, suffixId, male,
                    birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
                    organizationIds, roleIds, userGroupIds, sendEmail,
                    serviceContext);
        } finally {
            ServiceContextThreadLocal.setServiceContext(previousServiceContext);
        }
    }

    public void addUserGroupUsers(long userGroupId, long[] userIds)
            throws PortalException, SystemException {

        userGroupLocalService.copyUserGroupLayouts(userGroupId, userIds);

        userGroupPersistence.addUsers(userGroupId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int authenticateByEmailAddress(
            long companyId, String emailAddress, String password,
            Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
            throws PortalException, SystemException {

        return authenticate(
                companyId, emailAddress, password, CompanyConstants.AUTH_TYPE_EA,
                headerMap, parameterMap);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int authenticateByScreenName(
            long companyId, String screenName, String password,
            Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
            throws PortalException, SystemException {

        return authenticate(
                companyId, screenName, password, CompanyConstants.AUTH_TYPE_SN,
                headerMap, parameterMap);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int authenticateByUserId(
            long companyId, long userId, String password,
            Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
            throws PortalException, SystemException {

        return authenticate(
                companyId, String.valueOf(userId), password,
                CompanyConstants.AUTH_TYPE_ID, headerMap, parameterMap);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long authenticateForBasic(
            long companyId, String authType, String login, String password)
            throws PortalException, SystemException {

        try {
            User user = null;

            if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
                user = getUserByEmailAddress(companyId, login);
            } else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
                user = getUserByScreenName(companyId, login);
            } else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
                user = getUserById(companyId, GetterUtil.getLong(login));
            }

            if (!PropsValues.BASIC_AUTH_PASSWORD_REQUIRED) {
                return user.getUserId();
            }

            String userPassword = user.getPassword();

            if (!user.isPasswordEncrypted()) {
                userPassword = PwdEncryptor.encrypt(userPassword);
            }

            String encPassword = PwdEncryptor.encrypt(password);

            if (userPassword.equals(password)
                    || userPassword.equals(encPassword)) {

                return user.getUserId();
            }
        } catch (NoSuchUserException nsue) {
        }

        return 0;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean authenticateForJAAS(long userId, String encPassword) {
        try {
            User user = userPersistence.findByPrimaryKey(userId);

            if (user.isDefaultUser()) {
                _log.error(
                        "The default user should never be allowed to authenticate");

                return false;
            }

            String password = user.getPassword();

            if (user.isPasswordEncrypted()) {
                if (password.equals(encPassword)) {
                    return true;
                }

                if (!PropsValues.PORTAL_JAAS_STRICT_PASSWORD) {
                    encPassword = PwdEncryptor.encrypt(encPassword, password);

                    if (password.equals(encPassword)) {
                        return true;
                    }
                }
            } else {
                if (!PropsValues.PORTAL_JAAS_STRICT_PASSWORD) {
                    if (password.equals(encPassword)) {
                        return true;
                    }
                }

                password = PwdEncryptor.encrypt(password);

                if (password.equals(encPassword)) {
                    return true;
                }
            }
        } catch (Exception e) {
            _log.error(e);
        }

        return false;
    }

    public void checkLockout(User user)
            throws PortalException, SystemException {

        if (LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
            return;
        }

        PasswordPolicy passwordPolicy = user.getPasswordPolicy();

        if (passwordPolicy.isLockout()) {

            // Reset failure count

            Date now = new Date();
            int failedLoginAttempts = user.getFailedLoginAttempts();

            if (failedLoginAttempts > 0) {
                long failedLoginTime = user.getLastFailedLoginDate().getTime();
                long elapsedTime = now.getTime() - failedLoginTime;
                long requiredElapsedTime =
                        passwordPolicy.getResetFailureCount() * 1000;

                if ((requiredElapsedTime != 0)
                        && (elapsedTime > requiredElapsedTime)) {

                    user.setLastFailedLoginDate(null);
                    user.setFailedLoginAttempts(0);
                }
            }

            // Reset lockout

            if (user.isLockout()) {
                long lockoutTime = user.getLockoutDate().getTime();
                long elapsedTime = now.getTime() - lockoutTime;
                long requiredElapsedTime =
                        passwordPolicy.getLockoutDuration() * 1000;

                if ((requiredElapsedTime != 0)
                        && (elapsedTime > requiredElapsedTime)) {

                    user.setLockout(false);
                    user.setLockoutDate(null);
                }
            }

            if (user.isLockout()) {
                throw new UserLockoutException();
            }
        }
    }

    public void checkLoginFailure(User user) throws SystemException {
        Date now = new Date();

        int failedLoginAttempts = user.getFailedLoginAttempts();

        user.setLastFailedLoginDate(now);
        user.setFailedLoginAttempts(++failedLoginAttempts);

        userPersistence.update(user, false);
    }

    public void checkLoginFailureByEmailAddress(
            long companyId, String emailAddress)
            throws PortalException, SystemException {

        User user = getUserByEmailAddress(companyId, emailAddress);

        checkLoginFailure(user);
    }

    public void checkLoginFailureById(long userId)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        checkLoginFailure(user);
    }

    public void checkLoginFailureByScreenName(long companyId, String screenName)
            throws PortalException, SystemException {

        User user = getUserByScreenName(companyId, screenName);

        checkLoginFailure(user);
    }

    public void checkPasswordExpired(User user)
            throws PortalException, SystemException {

        if (LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
            return;
        }

        PasswordPolicy passwordPolicy = user.getPasswordPolicy();

        // Check if password has expired

        if (isPasswordExpired(user)) {
            int graceLoginCount = user.getGraceLoginCount();

            if (graceLoginCount < passwordPolicy.getGraceLimit()) {
                user.setGraceLoginCount(++graceLoginCount);

                userPersistence.update(user, false);
            } else {
                throw new PasswordExpiredException();
            }
        }

        // Check if warning message should be sent

        if (isPasswordExpiringSoon(user)) {
            user.setPasswordReset(true);

            userPersistence.update(user, false);
        }

        // Check if user should be forced to change password on first login

        if (passwordPolicy.isChangeable()
                && passwordPolicy.isChangeRequired()) {

            if (user.getLastLoginDate() == null) {
                boolean passwordReset = false;

                if (passwordPolicy.isChangeable()
                        && passwordPolicy.isChangeRequired()) {

                    passwordReset = true;
                }

                user.setPasswordReset(passwordReset);

                userPersistence.update(user, false);
            }
        }
    }

    public KeyValuePair decryptUserId(
            long companyId, String name, String password)
            throws PortalException, SystemException {

        Company company = companyPersistence.findByPrimaryKey(companyId);

        try {
            name = Encryptor.decrypt(company.getKeyObj(), name);
        } catch (EncryptorException ee) {
            throw new SystemException(ee);
        }

        long userId = GetterUtil.getLong(name);

        User user = userPersistence.findByPrimaryKey(userId);

        try {
            password = Encryptor.decrypt(company.getKeyObj(), password);
        } catch (EncryptorException ee) {
            throw new SystemException(ee);
        }

        String encPassword = PwdEncryptor.encrypt(password);

        if (user.getPassword().equals(encPassword)) {
            if (isPasswordExpired(user)) {
                user.setPasswordReset(true);

                userPersistence.update(user, false);
            }

            return new KeyValuePair(name, password);
        } else {
            throw new PrincipalException();
        }
    }

    public void deletePortrait(long userId)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        long portraitId = user.getPortraitId();

        if (portraitId > 0) {
            user.setPortraitId(0);

            userPersistence.update(user, false);

            imageLocalService.deleteImage(portraitId);
        }
    }

    public void deleteRoleUser(long roleId, long userId)
            throws PortalException, SystemException {

        rolePersistence.removeUser(roleId, userId);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userId);

        PermissionCacheUtil.clearCache();
    }

    public void deleteUser(long userId)
            throws PortalException, SystemException {

        if (!PropsValues.USERS_DELETE) {
            throw new RequiredUserException();
        }

        User user = userPersistence.findByPrimaryKey(userId);

        // Indexer

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.delete(user);

        // Browser tracker

        browserTrackerLocalService.deleteUserBrowserTracker(userId);

        // Group

        Group group = user.getGroup();

        if (group != null) {
            groupLocalService.deleteGroup(group.getGroupId());
        }

        // Portrait

        imageLocalService.deleteImage(user.getPortraitId());

        // Password policy relation

        passwordPolicyRelLocalService.deletePasswordPolicyRel(
                User.class.getName(), userId);

        // Old passwords

        passwordTrackerLocalService.deletePasswordTrackers(userId);

        // Subscriptions

        subscriptionLocalService.deleteSubscriptions(userId);

        // External user ids

        userIdMapperLocalService.deleteUserIdMappers(userId);

        // Announcements

        announcementsDeliveryLocalService.deleteDeliveries(userId);

        // Asset

        assetEntryLocalService.deleteEntry(User.class.getName(), userId);

        // Blogs

        blogsStatsUserLocalService.deleteStatsUserByUserId(userId);

        // Document library

        dlFileRankLocalService.deleteFileRanks(userId);

        // Expando

        expandoValueLocalService.deleteValues(User.class.getName(), userId);

        // Message boards

        mbBanLocalService.deleteBansByBanUserId(userId);
        mbMessageFlagLocalService.deleteFlags(userId);
        mbStatsUserLocalService.deleteStatsUsersByUserId(userId);

        // Shopping cart

        shoppingCartLocalService.deleteUserCarts(userId);

        // Social

        socialActivityLocalService.deleteUserActivities(userId);
        socialRequestLocalService.deleteReceiverUserRequests(userId);
        socialRequestLocalService.deleteUserRequests(userId);

        // Mail

        mailService.deleteUser(user.getCompanyId(), userId);

        // Contact

        try {
            contactLocalService.deleteContact(user.getContactId());
        } catch (NoSuchContactException nsce) {
        }

        // Resources

        resourceLocalService.deleteResource(
                user.getCompanyId(), User.class.getName(),
                ResourceConstants.SCOPE_INDIVIDUAL, user.getUserId());

        // Group roles

        userGroupRoleLocalService.deleteUserGroupRolesByUserId(userId);

        // User

        userPersistence.remove(userId);

        // Permission cache

        PermissionCacheUtil.clearCache();
    }

    public String encryptUserId(String name)
            throws PortalException, SystemException {

        long userId = GetterUtil.getLong(name);

        User user = userPersistence.findByPrimaryKey(userId);

        Company company = companyPersistence.findByPrimaryKey(
                user.getCompanyId());

        try {
            return Encryptor.encrypt(company.getKeyObj(), name);
        } catch (EncryptorException ee) {
            throw new SystemException(ee);
        }
    }

    public List<User> getCompanyUsers(long companyId, int start, int end)
            throws SystemException {

        return userPersistence.findByCompanyId(companyId, start, end);
    }

    public int getCompanyUsersCount(long companyId) throws SystemException {
        return userPersistence.countByCompanyId(companyId);
    }

    public User getDefaultUser(long companyId)
            throws PortalException, SystemException {

        User userModel = _defaultUsers.get(companyId);

        if (userModel == null) {
            userModel = userPersistence.findByC_DU(companyId, true);

            _defaultUsers.put(companyId, userModel);
        }

        return userModel;
    }

    public long getDefaultUserId(long companyId)
            throws PortalException, SystemException {

        User user = getDefaultUser(companyId);

        return user.getUserId();
    }

    public long[] getGroupUserIds(long groupId) throws SystemException {
        return getUserIds(getGroupUsers(groupId));
    }

    public List<User> getGroupUsers(long groupId) throws SystemException {
        return groupPersistence.getUsers(groupId);
    }

    public int getGroupUsersCount(long groupId) throws SystemException {
        return groupPersistence.getUsersSize(groupId);
    }

    public int getGroupUsersCount(long groupId, boolean active)
            throws PortalException, SystemException {

        Group group = groupPersistence.findByPrimaryKey(groupId);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put("usersGroups", new Long(groupId));

        return searchCount(group.getCompanyId(), null, active, params);
    }

    public List<User> getNoAnnouncementsDeliveries(String type)
            throws SystemException {

        return userFinder.findByNoAnnouncementsDeliveries(type);
    }

    public List<User> getNoContacts() throws SystemException {
        return userFinder.findByNoContacts();
    }

    public List<User> getNoGroups() throws SystemException {
        return userFinder.findByNoGroups();
    }

    public long[] getOrganizationUserIds(long organizationId)
            throws SystemException {

        return getUserIds(getOrganizationUsers(organizationId));
    }

    public List<User> getOrganizationUsers(long organizationId)
            throws SystemException {

        return organizationPersistence.getUsers(organizationId);
    }

    public int getOrganizationUsersCount(long organizationId)
            throws SystemException {

        return organizationPersistence.getUsersSize(organizationId);
    }

    public int getOrganizationUsersCount(long organizationId, boolean active)
            throws PortalException, SystemException {

        Organization organization = organizationPersistence.findByPrimaryKey(
                organizationId);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put("usersOrgs", new Long(organizationId));

        return searchCount(organization.getCompanyId(), null, active, params);
    }

    public long[] getRoleUserIds(long roleId) throws SystemException {
        return getUserIds(getRoleUsers(roleId));
    }

    public List<User> getRoleUsers(long roleId) throws SystemException {
        return rolePersistence.getUsers(roleId);
    }

    public List<User> getRoleUsers(long roleId, int start, int end)
            throws SystemException {

        return rolePersistence.getUsers(roleId, start, end);
    }

    public int getRoleUsersCount(long roleId) throws SystemException {
        return rolePersistence.getUsersSize(roleId);
    }

    public int getRoleUsersCount(long roleId, boolean active)
            throws PortalException, SystemException {

        Role role = rolePersistence.findByPrimaryKey(
                roleId);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put("usersRoles", new Long(roleId));

        return searchCount(role.getCompanyId(), null, active, params);
    }

    public List<User> getSocialUsers(
            long userId, int type, int start, int end, OrderByComparator obc)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put("socialRelationType", new Long[]{userId, new Long(type)});

        return search(user.getCompanyId(), null, true, params, start, end, obc);
    }

    public List<User> getSocialUsers(
            long userId, int start, int end, OrderByComparator obc)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put("socialRelation", new Long[]{userId});

        return search(
                user.getCompanyId(), null, true, params, start, end, obc);
    }

    public List<User> getSocialUsers(
            long userId1, long userId2, int type, int start, int end,
            OrderByComparator obc)
            throws PortalException, SystemException {

        User user1 = userPersistence.findByPrimaryKey(userId1);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put(
                "socialMutualRelationType",
                new Long[]{userId1, new Long(type), userId2, new Long(type)});

        return search(
                user1.getCompanyId(), null, true, params, start, end, obc);
    }

    public List<User> getSocialUsers(
            long userId1, long userId2, int start, int end,
            OrderByComparator obc)
            throws PortalException, SystemException {

        User user1 = userPersistence.findByPrimaryKey(userId1);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put("socialMutualRelation", new Long[]{userId1, userId2});

        return search(
                user1.getCompanyId(), null, true, params, start, end, obc);
    }

    public int getSocialUsersCount(long userId)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put("socialRelation", new Long[]{userId});

        return searchCount(user.getCompanyId(), null, true, params);
    }

    public int getSocialUsersCount(long userId, int type)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put("socialRelationType", new Long[]{userId, new Long(type)});

        return searchCount(user.getCompanyId(), null, true, params);
    }

    public int getSocialUsersCount(long userId1, long userId2)
            throws PortalException, SystemException {

        User user1 = userPersistence.findByPrimaryKey(userId1);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put("socialMutualRelation", new Long[]{userId1, userId2});

        return searchCount(user1.getCompanyId(), null, true, params);
    }

    public int getSocialUsersCount(long userId1, long userId2, int type)
            throws PortalException, SystemException {

        User user1 = userPersistence.findByPrimaryKey(userId1);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put(
                "socialMutualRelationType",
                new Long[]{userId1, new Long(type), userId2, new Long(type)});

        return searchCount(user1.getCompanyId(), null, true, params);
    }

    public User getUserByContactId(long contactId)
            throws PortalException, SystemException {

        return userPersistence.findByContactId(contactId);
    }

    public User getUserByEmailAddress(long companyId, String emailAddress)
            throws PortalException, SystemException {

        emailAddress = emailAddress.trim().toLowerCase();

        return userPersistence.findByC_EA(companyId, emailAddress);
    }

    public User getUserById(long userId)
            throws PortalException, SystemException {

        return userPersistence.findByPrimaryKey(userId);
    }

    public User getUserById(long companyId, long userId)
            throws PortalException, SystemException {

        return userPersistence.findByC_U(companyId, userId);
    }

    public User getUserByOpenId(String openId)
            throws PortalException, SystemException {

        return userPersistence.findByOpenId(openId);
    }

    public User getUserByPortraitId(long portraitId)
            throws PortalException, SystemException {

        return userPersistence.findByPortraitId(portraitId);
    }

    public User getUserByScreenName(long companyId, String screenName)
            throws PortalException, SystemException {

        screenName = getScreenName(screenName);

        return userPersistence.findByC_SN(companyId, screenName);
    }

    public User getUserByUuid(String uuid)
            throws PortalException, SystemException {

        List<User> users = userPersistence.findByUuid(uuid);

        if (users.isEmpty()) {
            throw new NoSuchUserException();
        } else {
            return users.get(0);
        }
    }

    public List<User> getUserGroupUsers(long userGroupId)
            throws SystemException {

        return userGroupPersistence.getUsers(userGroupId);
    }

    public int getUserGroupUsersCount(long userGroupId) throws SystemException {
        return userGroupPersistence.getUsersSize(userGroupId);
    }

    public int getUserGroupUsersCount(long userGroupId, boolean active)
            throws PortalException, SystemException {

        UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
                userGroupId);

        LinkedHashMap<String, Object> params =
                new LinkedHashMap<String, Object>();

        params.put("usersUserGroups", new Long(userGroupId));

        return searchCount(userGroup.getCompanyId(), null, active, params);
    }

    public long getUserIdByEmailAddress(long companyId, String emailAddress)
            throws PortalException, SystemException {

        emailAddress = emailAddress.trim().toLowerCase();

        User user = userPersistence.findByC_EA(companyId, emailAddress);

        return user.getUserId();
    }

    public long getUserIdByScreenName(long companyId, String screenName)
            throws PortalException, SystemException {

        screenName = getScreenName(screenName);

        User user = userPersistence.findByC_SN(companyId, screenName);

        return user.getUserId();
    }

    public boolean hasGroupUser(long groupId, long userId)
            throws SystemException {

        return groupPersistence.containsUser(groupId, userId);
    }

    public boolean hasOrganizationUser(long organizationId, long userId)
            throws SystemException {

        return organizationPersistence.containsUser(organizationId, userId);
    }

    public boolean hasPasswordPolicyUser(long passwordPolicyId, long userId)
            throws SystemException {

        return passwordPolicyRelLocalService.hasPasswordPolicyRel(
                passwordPolicyId, User.class.getName(), userId);
    }

    public boolean hasRoleUser(long roleId, long userId)
            throws SystemException {

        return rolePersistence.containsUser(roleId, userId);
    }

    /**
     * Returns true if the user has the role.
     *
     * @return true if the user has the role
     */
    public boolean hasRoleUser(
            long companyId, String name, long userId, boolean inherited)
            throws PortalException, SystemException {

        return roleLocalService.hasUserRole(userId, companyId, name, inherited);
    }

    public boolean hasTeamUser(long teamId, long userId)
            throws SystemException {

        return teamPersistence.containsUser(teamId, userId);
    }

    public boolean hasUserGroupUser(long userGroupId, long userId)
            throws SystemException {

        return userGroupPersistence.containsUser(userGroupId, userId);
    }

    public boolean isPasswordExpired(User user)
            throws PortalException, SystemException {

        PasswordPolicy passwordPolicy = user.getPasswordPolicy();

        if (passwordPolicy.getExpireable()) {
            Date now = new Date();

            if (user.getPasswordModifiedDate() == null) {
                user.setPasswordModifiedDate(now);

                userLocalService.updateUser(user, false);
            }

            long passwordStartTime = user.getPasswordModifiedDate().getTime();
            long elapsedTime = now.getTime() - passwordStartTime;

            if (elapsedTime > (passwordPolicy.getMaxAge() * 1000)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public boolean isPasswordExpiringSoon(User user)
            throws PortalException, SystemException {

        PasswordPolicy passwordPolicy = user.getPasswordPolicy();

        if (passwordPolicy.isExpireable()) {
            Date now = new Date();

            if (user.getPasswordModifiedDate() == null) {
                user.setPasswordModifiedDate(now);

                userLocalService.updateUser(user, false);
            }

            long timeModified = user.getPasswordModifiedDate().getTime();
            long passwordExpiresOn =
                    (passwordPolicy.getMaxAge() * 1000) + timeModified;

            long timeStartWarning =
                    passwordExpiresOn - (passwordPolicy.getWarningTime() * 1000);

            if (now.getTime() > timeStartWarning) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public List<User> search(
            long companyId, String keywords, Boolean active,
            LinkedHashMap<String, Object> params, int start, int end,
            OrderByComparator obc)
            throws SystemException {

        return userFinder.findByKeywords(
                companyId, keywords, active, params, start, end, obc);
    }

    public Hits search(
            long companyId, String keywords, Boolean active,
            LinkedHashMap<String, Object> params, int start, int end, Sort sort)
            throws SystemException {

        String firstName = null;
        String middleName = null;
        String lastName = null;
        String screenName = null;
        String emailAddress = null;
        boolean andOperator = false;

        if (Validator.isNotNull(keywords)) {
            firstName = keywords;
            middleName = keywords;
            lastName = keywords;
            screenName = keywords;
            emailAddress = keywords;
        } else {
            andOperator = true;
        }

        return search(
                companyId, firstName, middleName, lastName, screenName,
                emailAddress, active, params, andOperator, start, end, sort);
    }

    public List<User> search(
            long companyId, String firstName, String middleName,
            String lastName, String screenName, String emailAddress,
            Boolean active, LinkedHashMap<String, Object> params,
            boolean andSearch, int start, int end, OrderByComparator obc)
            throws SystemException {

        return userFinder.findByC_FN_MN_LN_SN_EA_A(
                companyId, firstName, middleName, lastName, screenName,
                emailAddress, active, params, andSearch, start, end, obc);
    }

    public Hits search(
            long companyId, String firstName, String middleName,
            String lastName, String screenName, String emailAddress,
            Boolean active, LinkedHashMap<String, Object> params,
            boolean andSearch, int start, int end, Sort sort)
            throws SystemException {

        try {
            Map<String, Serializable> attributes =
                    new HashMap<String, Serializable>();

            attributes.put("active", active);
            attributes.put("andSearch", andSearch);
            attributes.put("emailAddress", emailAddress);
            attributes.put("firstName", firstName);
            attributes.put("lastName", lastName);
            attributes.put("middleName", middleName);
            attributes.put("params", params);
            attributes.put("screenName", screenName);

            SearchContext searchContext = new SearchContext();

            searchContext.setAttributes(attributes);
            searchContext.setCompanyId(companyId);
            searchContext.setEnd(end);
            searchContext.setSorts(new Sort[]{sort});
            searchContext.setStart(start);

            Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

            return indexer.search(searchContext);
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    public int searchCount(
            long companyId, String keywords, Boolean active,
            LinkedHashMap<String, Object> params)
            throws SystemException {

        return userFinder.countByKeywords(companyId, keywords, active, params);
    }

    public int searchCount(
            long companyId, String firstName, String middleName,
            String lastName, String screenName, String emailAddress,
            Boolean active, LinkedHashMap<String, Object> params,
            boolean andSearch)
            throws SystemException {

        return userFinder.countByC_FN_MN_LN_SN_EA_A(
                companyId, firstName, middleName, lastName, screenName,
                emailAddress, active, params, andSearch);
    }

    public void sendPassword(
            long companyId, String emailAddress, String remoteAddr,
            String remoteHost, String userAgent, String fromName,
            String fromAddress, String subject, String body)
            throws PortalException, SystemException {

        try {
            doSendPassword(
                    companyId, emailAddress, remoteAddr, remoteHost, userAgent,
                    fromName, fromAddress, subject, body);
        } catch (IOException ioe) {
            throw new SystemException(ioe);
        }
    }

    public void setRoleUsers(long roleId, long[] userIds)
            throws PortalException, SystemException {

        rolePersistence.setUsers(roleId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public void setUserGroupUsers(long userGroupId, long[] userIds)
            throws PortalException, SystemException {

        userGroupLocalService.copyUserGroupLayouts(userGroupId, userIds);

        userGroupPersistence.setUsers(userGroupId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public void unsetGroupUsers(long groupId, long[] userIds)
            throws PortalException, SystemException {

        userGroupRoleLocalService.deleteUserGroupRoles(userIds, groupId);

        groupPersistence.removeUsers(groupId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public void unsetOrganizationUsers(long organizationId, long[] userIds)
            throws PortalException, SystemException {

        Organization organization = organizationPersistence.findByPrimaryKey(
                organizationId);

        Group group = organization.getGroup();

        long groupId = group.getGroupId();

        userGroupRoleLocalService.deleteUserGroupRoles(userIds, groupId);

        organizationPersistence.removeUsers(organizationId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public void unsetPasswordPolicyUsers(
            long passwordPolicyId, long[] userIds)
            throws SystemException {

        passwordPolicyRelLocalService.deletePasswordPolicyRels(
                passwordPolicyId, User.class.getName(), userIds);
    }

    public void unsetRoleUsers(long roleId, List<User> users)
            throws PortalException, SystemException {

        Role role = rolePersistence.findByPrimaryKey(roleId);

        if (role.getName().equals(RoleConstants.USER)) {
            return;
        }

        rolePersistence.removeUsers(roleId, users);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(users);

        PermissionCacheUtil.clearCache();
    }

    public void unsetRoleUsers(long roleId, long[] userIds)
            throws PortalException, SystemException {

        Role role = rolePersistence.findByPrimaryKey(roleId);

        if (role.getName().equals(RoleConstants.USER)) {
            return;
        }

        rolePersistence.removeUsers(roleId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public void unsetTeamUsers(long teamId, long[] userIds)
            throws PortalException, SystemException {

        teamPersistence.removeUsers(teamId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public void unsetUserGroupUsers(long userGroupId, long[] userIds)
            throws PortalException, SystemException {

        userGroupPersistence.removeUsers(userGroupId, userIds);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(userIds);

        PermissionCacheUtil.clearCache();
    }

    public User updateActive(long userId, boolean active)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        user.setActive(active);

        userPersistence.update(user, false);

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(user);

        if (active) {
            String from = "math.app.fpm@gmail.com";
            String to = user.getEmailAddress();

            String subject = "Вітаємо!";

            StringBuilder body = new StringBuilder("\n");
            body.append("Dear ").append(user.getFullName()).append("!\n")
                    .append("You successfully registered at www.primat.dp.ua\n");
            try {
                MailEngine.send(from, to, subject, body.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public User updateAgreedToTermsOfUse(
            long userId, boolean agreedToTermsOfUse)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        user.setAgreedToTermsOfUse(agreedToTermsOfUse);

        userPersistence.update(user, false);

        return user;
    }

    public void updateAsset(
            long userId, User user, long[] assetCategoryIds,
            String[] assetTagNames)
            throws PortalException, SystemException {

        User owner = userPersistence.findByPrimaryKey(userId);

        Company company = companyPersistence.findByPrimaryKey(
                owner.getCompanyId());

        Group companyGroup = company.getGroup();

        assetEntryLocalService.updateEntry(
                userId, companyGroup.getGroupId(), User.class.getName(),
                user.getUserId(), assetCategoryIds, assetTagNames, false, null,
                null, null, null, null, user.getFullName(), null, null, null, 0, 0,
                null, false);
    }

    public User updateCreateDate(long userId, Date createDate)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        user.setCreateDate(createDate);

        userPersistence.update(user, false);

        return user;
    }

    public User updateEmailAddress(
            long userId, String password, String emailAddress1,
            String emailAddress2)
            throws PortalException, SystemException {

        emailAddress1 = emailAddress1.trim().toLowerCase();
        emailAddress2 = emailAddress2.trim().toLowerCase();

        if (!emailAddress1.equals(emailAddress2)) {
            throw new UserEmailAddressException();
        }

        User user = userPersistence.findByPrimaryKey(userId);

        validateEmailAddress(user.getCompanyId(), emailAddress1);
        validateEmailAddress(user.getCompanyId(), emailAddress2);

        if (!user.getEmailAddress().equalsIgnoreCase(emailAddress1)) {
            if (userPersistence.fetchByC_EA(
                    user.getCompanyId(), emailAddress1) != null) {

                throw new DuplicateUserEmailAddressException();
            }
        }

        setEmailAddress(
                user, password, user.getFirstName(), user.getMiddleName(),
                user.getLastName(), emailAddress1);

        userPersistence.update(user, false);

        return user;
    }

    public void updateGroups(long userId, long[] newGroupIds)
            throws PortalException, SystemException {

        if (newGroupIds == null) {
            return;
        }

        List<Group> oldGroups = userPersistence.getGroups(userId);

        List<Long> oldGroupIds = new ArrayList<Long>(oldGroups.size());

        for (Group oldGroup : oldGroups) {
            long oldGroupId = oldGroup.getGroupId();

            oldGroupIds.add(oldGroupId);

            if (!ArrayUtil.contains(newGroupIds, oldGroupId)) {
                unsetGroupUsers(oldGroupId, new long[]{userId});
            }
        }

        for (long newGroupId : newGroupIds) {
            if (!oldGroupIds.contains(newGroupId)) {
                addGroupUsers(newGroupId, new long[]{userId});
            }
        }

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(new long[]{userId});

        PermissionCacheUtil.clearCache();
    }

    public User updateLastLogin(long userId, String loginIP)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        Date lastLoginDate = user.getLoginDate();

        if (lastLoginDate == null) {
            lastLoginDate = new Date();
        }

        user.setLoginDate(new Date());
        user.setLoginIP(loginIP);
        user.setLastLoginDate(lastLoginDate);
        user.setLastLoginIP(user.getLoginIP());
        user.setLastFailedLoginDate(null);
        user.setFailedLoginAttempts(0);

        userPersistence.update(user, false);

        return user;
    }

    public User updateLockout(User user, boolean lockout)
            throws PortalException, SystemException {

        PasswordPolicy passwordPolicy = user.getPasswordPolicy();

        if ((passwordPolicy == null) || !passwordPolicy.isLockout()) {
            return user;
        }

        Date lockoutDate = null;

        if (lockout) {
            lockoutDate = new Date();
        }

        user.setLockout(lockout);
        user.setLockoutDate(lockoutDate);

        if (!lockout) {
            user.setLastFailedLoginDate(lockoutDate);
            user.setFailedLoginAttempts(0);
        }

        userPersistence.update(user, false);

        return user;
    }

    public User updateLockoutByEmailAddress(
            long companyId, String emailAddress, boolean lockout)
            throws PortalException, SystemException {

        User user = getUserByEmailAddress(companyId, emailAddress);

        return updateLockout(user, lockout);
    }

    public User updateLockoutById(long userId, boolean lockout)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        return updateLockout(user, lockout);
    }

    public User updateLockoutByScreenName(
            long companyId, String screenName, boolean lockout)
            throws PortalException, SystemException {

        User user = getUserByScreenName(companyId, screenName);

        return updateLockout(user, lockout);
    }

    public User updateModifiedDate(long userId, Date modifiedDate)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        user.setModifiedDate(modifiedDate);

        userPersistence.update(user, false);

        return user;
    }

    public void updateOpenId(long userId, String openId)
            throws PortalException, SystemException {

        openId = openId.trim();

        User user = userPersistence.findByPrimaryKey(userId);

        user.setOpenId(openId);

        userPersistence.update(user, false);
    }

    public void updateOrganizations(long userId, long[] newOrganizationIds)
            throws PortalException, SystemException {

        if (newOrganizationIds == null) {
            return;
        }

        List<Organization> oldOrganizations = userPersistence.getOrganizations(
                userId);

        List<Long> oldOrganizationIds = new ArrayList<Long>(
                oldOrganizations.size());

        for (Organization oldOrganization : oldOrganizations) {
            long oldOrganizationId = oldOrganization.getOrganizationId();

            oldOrganizationIds.add(oldOrganizationId);

            if (!ArrayUtil.contains(newOrganizationIds, oldOrganizationId)) {
                unsetOrganizationUsers(oldOrganizationId, new long[]{userId});
            }
        }

        for (long newOrganizationId : newOrganizationIds) {
            if (!oldOrganizationIds.contains(newOrganizationId)) {
                addOrganizationUsers(newOrganizationId, new long[]{userId});
            }
        }

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(new long[]{userId});

        PermissionCacheUtil.clearCache();
    }

    public User updatePassword(
            long userId, String password1, String password2,
            boolean passwordReset)
            throws PortalException, SystemException {

        return updatePassword(
                userId, password1, password2, passwordReset, false);
    }

    public User updatePassword(
            long userId, String password1, String password2,
            boolean passwordReset, boolean silentUpdate)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        // Use silentUpdate so that imported user passwords are not exported,
        // tracked, or validated

        if (!silentUpdate) {
            validatePassword(user.getCompanyId(), userId, password1, password2);
        }

        String oldEncPwd = user.getPassword();

        if (!user.isPasswordEncrypted()) {
            oldEncPwd = PwdEncryptor.encrypt(user.getPassword());
        }

        String newEncPwd = PwdEncryptor.encrypt(password1);

        if (user.hasCompanyMx()) {
            mailService.updatePassword(user.getCompanyId(), userId, password1);
        }

        user.setPassword(newEncPwd);
        user.setPasswordUnencrypted(password1);
        user.setPasswordEncrypted(true);
        user.setPasswordReset(passwordReset);
        user.setPasswordModifiedDate(new Date());
        user.setGraceLoginCount(0);

        if (!silentUpdate) {
            user.setPasswordModified(true);
        }

        try {
            userPersistence.update(user, false);
        } catch (ModelListenerException mle) {
            String msg = GetterUtil.getString(mle.getCause().getMessage());

            if (LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
                String passwordHistory = PrefsPropsUtil.getString(
                        user.getCompanyId(), PropsKeys.LDAP_ERROR_PASSWORD_HISTORY);

                if (msg.indexOf(passwordHistory) != -1) {
                    throw new UserPasswordException(
                            UserPasswordException.PASSWORD_ALREADY_USED);
                }
            }

            throw new UserPasswordException(
                    UserPasswordException.PASSWORD_INVALID);
        }

        if (!silentUpdate) {
            user.setPasswordModified(false);

            passwordTrackerLocalService.trackPassword(userId, oldEncPwd);
        }

        return user;
    }

    public User updatePasswordManually(
            long userId, String password, boolean passwordEncrypted,
            boolean passwordReset, Date passwordModifiedDate)
            throws PortalException, SystemException {

        // This method should only be used to manually massage data

        User user = userPersistence.findByPrimaryKey(userId);

        user.setPassword(password);
        user.setPasswordEncrypted(passwordEncrypted);
        user.setPasswordReset(passwordReset);
        user.setPasswordModifiedDate(passwordModifiedDate);

        userPersistence.update(user, false);

        return user;
    }

    public void updatePasswordReset(long userId, boolean passwordReset)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        user.setPasswordReset(passwordReset);

        userPersistence.update(user, false);
    }

    public void updatePortrait(long userId, byte[] bytes)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        long imageMaxSize = PrefsPropsUtil.getLong(
                PropsKeys.USERS_IMAGE_MAX_SIZE);

        if ((imageMaxSize > 0)
                && ((bytes == null) || (bytes.length > imageMaxSize))) {

            throw new UserPortraitSizeException();
        }

        long portraitId = user.getPortraitId();

        if (portraitId <= 0) {
            portraitId = counterLocalService.increment();

            user.setPortraitId(portraitId);

            userPersistence.update(user, false);
        }

        try {
            ImageBag imageBag = ImageProcessorUtil.read(bytes);

            RenderedImage renderedImage = imageBag.getRenderedImage();

            if (renderedImage == null) {
                throw new UserPortraitTypeException();
            }

            renderedImage = ImageProcessorUtil.scale(
                    renderedImage, PropsValues.USERS_IMAGE_MAX_HEIGHT,
                    PropsValues.USERS_IMAGE_MAX_WIDTH);

            String contentType = imageBag.getType();

            imageLocalService.updateImage(
                    portraitId,
                    ImageProcessorUtil.getBytes(renderedImage, contentType));
        } catch (IOException ioe) {
            throw new ImageSizeException(ioe);
        }
    }

    public void updateReminderQuery(long userId, String question, String answer)
            throws PortalException, SystemException {

        validateReminderQuery(question, answer);

        User user = userPersistence.findByPrimaryKey(userId);

        user.setReminderQueryQuestion(question);
        user.setReminderQueryAnswer(answer);

        userPersistence.update(user, false);
    }

    public void updateScreenName(long userId, String screenName)
            throws PortalException, SystemException {

        // User

        User user = userPersistence.findByPrimaryKey(userId);

        screenName = getScreenName(screenName);

        validateScreenName(user.getCompanyId(), userId, screenName);

        user.setScreenName(screenName);

        userPersistence.update(user, false);

        // Group

        Group group = groupLocalService.getUserGroup(
                user.getCompanyId(), userId);

        group.setFriendlyURL(StringPool.SLASH + screenName);

        groupPersistence.update(group, false);
    }

    public User updateUser(
            long userId, String oldPassword, String newPassword1,
            String newPassword2, boolean passwordReset,
            String reminderQueryQuestion, String reminderQueryAnswer,
            String screenName, String emailAddress, String openId,
            String languageId, String timeZoneId, String greeting,
            String comments, String firstName, String middleName,
            String lastName, int prefixId, int suffixId, boolean male,
            int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn,
            String aimSn, String facebookSn, String icqSn, String jabberSn,
            String msnSn, String mySpaceSn, String skypeSn, String twitterSn,
            String ymSn, String jobTitle, long[] groupIds,
            long[] organizationIds, long[] roleIds,
            List<UserGroupRole> userGroupRoles, long[] userGroupIds,
            ServiceContext serviceContext)
            throws PortalException, SystemException {

        ServiceContext previousServiceContext =
                ServiceContextThreadLocal.getServiceContext();

        ServiceContextThreadLocal.setServiceContext(serviceContext);

        try {
            return doUpdateUser(
                    userId, oldPassword, newPassword1, newPassword2, passwordReset,
                    reminderQueryQuestion, reminderQueryAnswer, screenName,
                    emailAddress, openId, languageId, timeZoneId, greeting,
                    comments, firstName, middleName, lastName, prefixId, suffixId,
                    male, birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn,
                    facebookSn, icqSn, jabberSn, msnSn, mySpaceSn, skypeSn,
                    twitterSn, ymSn, jobTitle, groupIds, organizationIds, roleIds,
                    userGroupRoles, userGroupIds, serviceContext);
        } finally {
            ServiceContextThreadLocal.setServiceContext(previousServiceContext);
        }
    }

    protected int authenticate(
            long companyId, String login, String password, String authType,
            Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
            throws PortalException, SystemException {

        login = login.trim().toLowerCase();

        long userId = GetterUtil.getLong(login);

        // User input validation

        if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
            if (!Validator.isEmailAddress(login)) {
                throw new UserEmailAddressException();
            }
        } else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
            if (Validator.isNull(login)) {
                throw new UserScreenNameException();
            }
        } else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
            if (Validator.isNull(login)) {
                throw new UserIdException();
            }
        }

        if (Validator.isNull(password)) {
            throw new UserPasswordException(
                    UserPasswordException.PASSWORD_INVALID);
        }

        int authResult = Authenticator.FAILURE;

        // Pre-authentication pipeline

        if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
            authResult = AuthPipeline.authenticateByEmailAddress(
                    PropsKeys.AUTH_PIPELINE_PRE, companyId, login, password,
                    headerMap, parameterMap);
        } else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
            authResult = AuthPipeline.authenticateByScreenName(
                    PropsKeys.AUTH_PIPELINE_PRE, companyId, login, password,
                    headerMap, parameterMap);
        } else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
            authResult = AuthPipeline.authenticateByUserId(
                    PropsKeys.AUTH_PIPELINE_PRE, companyId, userId, password,
                    headerMap, parameterMap);
        }

        // Get user

        User user = null;

        try {
            if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
                user = userPersistence.findByC_EA(companyId, login);
            } else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
                user = userPersistence.findByC_SN(companyId, login);
            } else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
                user = userPersistence.findByC_U(
                        companyId, GetterUtil.getLong(login));
            }
        } catch (NoSuchUserException nsue) {
            return Authenticator.DNE;
        }

        if (user.isDefaultUser()) {
            _log.error(
                    "The default user should never be allowed to authenticate");

            return Authenticator.DNE;
        }

        if (!user.isPasswordEncrypted()) {
            user.setPassword(PwdEncryptor.encrypt(user.getPassword()));
            user.setPasswordEncrypted(true);

            userPersistence.update(user, false);
        }

        // Check password policy to see if the is account locked out or if the
        // password is expired

        checkLockout(user);

        checkPasswordExpired(user);

        // Authenticate against the User_ table

        if (authResult == Authenticator.SUCCESS) {
            if (PropsValues.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK) {
                String encPassword = PwdEncryptor.encrypt(
                        password, user.getPassword());

                if (user.getPassword().equals(encPassword)) {
                    authResult = Authenticator.SUCCESS;
                } else if (GetterUtil.getBoolean(PropsUtil.get(
                        PropsKeys.AUTH_MAC_ALLOW))) {

                    try {
                        MessageDigest digester = MessageDigest.getInstance(
                                PropsUtil.get(PropsKeys.AUTH_MAC_ALGORITHM));

                        digester.update(login.getBytes("UTF8"));

                        String shardKey =
                                PropsUtil.get(PropsKeys.AUTH_MAC_SHARED_KEY);

                        encPassword = Base64.encode(
                                digester.digest(shardKey.getBytes("UTF8")));

                        if (password.equals(encPassword)) {
                            authResult = Authenticator.SUCCESS;
                        } else {
                            authResult = Authenticator.FAILURE;
                        }
                    } catch (NoSuchAlgorithmException nsae) {
                        throw new SystemException(nsae);
                    } catch (UnsupportedEncodingException uee) {
                        throw new SystemException(uee);
                    }
                } else {
                    authResult = Authenticator.FAILURE;
                }
            }
        }

        // Post-authentication pipeline

        if (authResult == Authenticator.SUCCESS) {
            if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
                authResult = AuthPipeline.authenticateByEmailAddress(
                        PropsKeys.AUTH_PIPELINE_POST, companyId, login, password,
                        headerMap, parameterMap);
            } else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
                authResult = AuthPipeline.authenticateByScreenName(
                        PropsKeys.AUTH_PIPELINE_POST, companyId, login, password,
                        headerMap, parameterMap);
            } else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
                authResult = AuthPipeline.authenticateByUserId(
                        PropsKeys.AUTH_PIPELINE_POST, companyId, userId, password,
                        headerMap, parameterMap);
            }
        }

        // Execute code triggered by authentication failure

        if (authResult == Authenticator.FAILURE) {
            try {
                if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
                    AuthPipeline.onFailureByEmailAddress(
                            PropsKeys.AUTH_FAILURE, companyId, login, headerMap,
                            parameterMap);
                } else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
                    AuthPipeline.onFailureByScreenName(
                            PropsKeys.AUTH_FAILURE, companyId, login, headerMap,
                            parameterMap);
                } else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
                    AuthPipeline.onFailureByUserId(
                            PropsKeys.AUTH_FAILURE, companyId, userId, headerMap,
                            parameterMap);
                }

                // Let LDAP handle max failure event

                if (!LDAPSettingsUtil.isPasswordPolicyEnabled(
                        user.getCompanyId())) {

                    PasswordPolicy passwordPolicy = user.getPasswordPolicy();

                    int failedLoginAttempts = user.getFailedLoginAttempts();
                    int maxFailures = passwordPolicy.getMaxFailure();

                    if ((failedLoginAttempts >= maxFailures)
                            && (maxFailures != 0)) {

                        if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
                            AuthPipeline.onMaxFailuresByEmailAddress(
                                    PropsKeys.AUTH_MAX_FAILURES, companyId, login,
                                    headerMap, parameterMap);
                        } else if (authType.equals(
                                CompanyConstants.AUTH_TYPE_SN)) {

                            AuthPipeline.onMaxFailuresByScreenName(
                                    PropsKeys.AUTH_MAX_FAILURES, companyId, login,
                                    headerMap, parameterMap);
                        } else if (authType.equals(
                                CompanyConstants.AUTH_TYPE_ID)) {

                            AuthPipeline.onMaxFailuresByUserId(
                                    PropsKeys.AUTH_MAX_FAILURES, companyId, userId,
                                    headerMap, parameterMap);
                        }
                    }
                }
            } catch (Exception e) {
                _log.error(e, e);
            }
        }

        return authResult;
    }

    protected User doAddUser(
            long creatorUserId, long companyId, boolean autoPassword,
            String password1, String password2, boolean autoScreenName,
            String screenName, String emailAddress, String openId,
            Locale locale, String firstName, String middleName, String lastName,
            int prefixId, int suffixId, boolean male, int birthdayMonth,
            int birthdayDay, int birthdayYear, String jobTitle, long[] groupIds,
            long[] organizationIds, long[] roleIds, long[] userGroupIds,
            boolean sendEmail, ServiceContext serviceContext)
            throws PortalException, SystemException {

        // User

        Company company = companyPersistence.findByPrimaryKey(companyId);
        screenName = getScreenName(screenName);
        emailAddress = emailAddress.trim().toLowerCase();
        openId = openId.trim();
        Date now = new Date();

        if (PrefsPropsUtil.getBoolean(
                companyId, PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE)) {

            autoScreenName = true;
        }

        long userId = counterLocalService.increment();

        EmailAddressGenerator emailAddressGenerator =
                EmailAddressGeneratorFactory.getInstance();

        if (emailAddressGenerator.isGenerated(emailAddress)) {
            emailAddress = StringPool.BLANK;
        }

        if (!PropsValues.USERS_EMAIL_ADDRESS_REQUIRED
                && Validator.isNull(emailAddress)) {

            emailAddress = emailAddressGenerator.generate(companyId, userId);
        }

        validate(
                companyId, userId, autoPassword, password1, password2,
                autoScreenName, screenName, emailAddress, firstName, middleName,
                lastName, organizationIds);

        if (autoPassword) {
            password1 = PwdToolkitUtil.generate();
        } else {
            if (Validator.isNull(password1) || Validator.isNull(password2)) {
                throw new UserPasswordException(
                        UserPasswordException.PASSWORD_INVALID);
            }
        }

        if (autoScreenName) {
            ScreenNameGenerator screenNameGenerator =
                    ScreenNameGeneratorFactory.getInstance();

            try {
                screenName = screenNameGenerator.generate(
                        companyId, userId, emailAddress);
            } catch (Exception e) {
                throw new SystemException(e);
            }
        }

        User defaultUser = getDefaultUser(companyId);

        FullNameGenerator fullNameGenerator =
                FullNameGeneratorFactory.getInstance();

        String fullName = fullNameGenerator.getFullName(
                firstName, middleName, lastName);

        String greeting = LanguageUtil.format(
                locale, "welcome-x", " " + fullName, false);

        User user = userPersistence.create(userId);

        user.setCompanyId(companyId);
        user.setCreateDate(now);
        user.setModifiedDate(now);
        user.setDefaultUser(false);
        user.setContactId(counterLocalService.increment());
        user.setPassword(PwdEncryptor.encrypt(password1));
        user.setPasswordUnencrypted(password1);
        user.setPasswordEncrypted(true);
        user.setPasswordReset(false);
        user.setScreenName(screenName);
        user.setEmailAddress(emailAddress);
        user.setOpenId(openId);
        user.setLanguageId(locale.toString());
        user.setTimeZoneId(defaultUser.getTimeZoneId());
        user.setGreeting(greeting);
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setJobTitle(jobTitle);
        user.setActive(true);

        userPersistence.update(user, false);

        // Resources

        String creatorUserName = StringPool.BLANK;

        if (creatorUserId <= 0) {
            creatorUserId = user.getUserId();

            // Don't grab the full name from the User object because it doesn't
            // have a corresponding Contact object yet

            //creatorUserName = user.getFullName();
        } else {
            User creatorUser = userPersistence.findByPrimaryKey(creatorUserId);

            creatorUserName = creatorUser.getFullName();
        }

        resourceLocalService.addResources(
                companyId, 0, creatorUserId, User.class.getName(), user.getUserId(),
                false, false, false);

        // Mail

        if (user.hasCompanyMx()) {
            mailService.addUser(
                    companyId, userId, password1, firstName, middleName, lastName,
                    emailAddress);
        }

        // Contact

        Date birthday = PortalUtil.getDate(
                birthdayMonth, birthdayDay, birthdayYear,
                new ContactBirthdayException());

        Contact contact = contactPersistence.create(user.getContactId());

        contact.setCompanyId(user.getCompanyId());
        contact.setUserId(creatorUserId);
        contact.setUserName(creatorUserName);
        contact.setCreateDate(now);
        contact.setModifiedDate(now);
        contact.setAccountId(company.getAccountId());
        contact.setParentContactId(ContactConstants.DEFAULT_PARENT_CONTACT_ID);
        contact.setFirstName(firstName);
        contact.setMiddleName(middleName);
        contact.setLastName(lastName);
        contact.setPrefixId(prefixId);
        contact.setSuffixId(suffixId);
        contact.setMale(male);
        contact.setBirthday(birthday);
        contact.setJobTitle(jobTitle);

        contactPersistence.update(contact, false);

        // Group

        groupLocalService.addGroup(
                user.getUserId(), User.class.getName(), user.getUserId(), null,
                null, 0, StringPool.SLASH + screenName, true, null);

        // Groups

        if (groupIds != null) {
            groupLocalService.addUserGroups(userId, groupIds);
        }

        addDefaultGroups(userId);

        // Organizations

        updateOrganizations(userId, organizationIds);

        // Roles

        if (roleIds != null) {
            roleIds = EnterpriseAdminUtil.addRequiredRoles(user, roleIds);

            userPersistence.setRoles(userId, roleIds);
        }

        addDefaultRoles(userId);

        // User groups

        if (userGroupIds != null) {
            for (long userGroupId : userGroupIds) {
                userGroupLocalService.copyUserGroupLayouts(
                        userGroupId, new long[]{userId});
            }

            userPersistence.setUserGroups(userId, userGroupIds);
        }

        addDefaultUserGroups(userId);

        // Asset

        if (serviceContext != null) {
            updateAsset(
                    creatorUserId, user, serviceContext.getAssetCategoryIds(),
                    serviceContext.getAssetTagNames());
        }

        // Expando

        user.setExpandoBridgeAttributes(serviceContext);

        // Email

        if (sendEmail) {
            try {
                sendEmail(user, password1);
            } catch (IOException ioe) {
                throw new SystemException(ioe);
            }
        }

        // Indexer

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(user);

        return user;
    }

    protected void doSendPassword(
            long companyId, String emailAddress, String remoteAddr,
            String remoteHost, String userAgent, String fromName,
            String fromAddress, String subject, String body)
            throws IOException, PortalException, SystemException {

        if (!PrefsPropsUtil.getBoolean(
                companyId, PropsKeys.COMPANY_SECURITY_SEND_PASSWORD)
                || !PrefsPropsUtil.getBoolean(
                companyId, PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_ENABLED)) {

            return;
        }

        emailAddress = emailAddress.trim().toLowerCase();

        if (!Validator.isEmailAddress(emailAddress)) {
            throw new UserEmailAddressException();
        }

        Company company = companyPersistence.findByPrimaryKey(companyId);

        User user = userPersistence.findByC_EA(companyId, emailAddress);

        PasswordPolicy passwordPolicy = user.getPasswordPolicy();

        /*if (user.hasCompanyMx()) {
        throw new SendPasswordException();
        }*/

        String newPassword = null;

        if (!PwdEncryptor.PASSWORDS_ENCRYPTION_ALGORITHM.equals(
                PwdEncryptor.TYPE_NONE)) {

            newPassword = PwdToolkitUtil.generate();

            boolean passwordReset = false;

            if (passwordPolicy.getChangeable()
                    && passwordPolicy.getChangeRequired()) {

                passwordReset = true;
            }

            user.setPassword(PwdEncryptor.encrypt(newPassword));
            user.setPasswordUnencrypted(newPassword);
            user.setPasswordEncrypted(true);
            user.setPasswordReset(passwordReset);
            user.setPasswordModified(true);
            user.setPasswordModifiedDate(new Date());

            userPersistence.update(user, false);

            user.setPasswordModified(false);
        } else {
            newPassword = user.getPassword();
        }

        if (Validator.isNull(fromName)) {
            fromName = PrefsPropsUtil.getString(
                    companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
        }

        if (Validator.isNull(fromAddress)) {
            fromAddress = PrefsPropsUtil.getString(
                    companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
        }

        String toName = user.getFullName();
        String toAddress = user.getEmailAddress();

        if (Validator.isNull(subject)) {
            subject = PrefsPropsUtil.getContent(
                    companyId, PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT);
        }

        if (Validator.isNull(body)) {
            body = PrefsPropsUtil.getContent(
                    companyId, PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY);
        }

        subject = StringUtil.replace(
                subject,
                new String[]{
                    "[$FROM_ADDRESS$]",
                    "[$FROM_NAME$]",
                    "[$PORTAL_URL$]",
                    "[$REMOTE_ADDRESS$]",
                    "[$REMOTE_HOST$]",
                    "[$TO_ADDRESS$]",
                    "[$TO_NAME$]",
                    "[$USER_AGENT$]",
                    "[$USER_ID$]",
                    "[$USER_PASSWORD$]",
                    "[$USER_SCREENNAME$]"
                },
                new String[]{
                    fromAddress,
                    fromName,
                    company.getVirtualHost(),
                    remoteAddr,
                    remoteHost,
                    toAddress,
                    toName,
                    HtmlUtil.escape(userAgent),
                    String.valueOf(user.getUserId()),
                    newPassword,
                    user.getScreenName()
                });

        body = StringUtil.replace(
                body,
                new String[]{
                    "[$FROM_ADDRESS$]",
                    "[$FROM_NAME$]",
                    "[$PORTAL_URL$]",
                    "[$REMOTE_ADDRESS$]",
                    "[$REMOTE_HOST$]",
                    "[$TO_ADDRESS$]",
                    "[$TO_NAME$]",
                    "[$USER_AGENT$]",
                    "[$USER_ID$]",
                    "[$USER_PASSWORD$]",
                    "[$USER_SCREENNAME$]"
                },
                new String[]{
                    fromAddress,
                    fromName,
                    company.getVirtualHost(),
                    remoteAddr,
                    remoteHost,
                    toAddress,
                    toName,
                    HtmlUtil.escape(userAgent),
                    String.valueOf(user.getUserId()),
                    newPassword,
                    user.getScreenName()
                });

        InternetAddress from = new InternetAddress(fromAddress, fromName);

        InternetAddress to = new InternetAddress(toAddress, toName);

        MailMessage message = new MailMessage(from, to, subject, body, true);

        mailService.sendEmail(message);
    }

    protected User doUpdateUser(
            long userId, String oldPassword, String newPassword1,
            String newPassword2, boolean passwordReset,
            String reminderQueryQuestion, String reminderQueryAnswer,
            String screenName, String emailAddress, String openId,
            String languageId, String timeZoneId, String greeting,
            String comments, String firstName, String middleName,
            String lastName, int prefixId, int suffixId, boolean male,
            int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn,
            String aimSn, String facebookSn, String icqSn, String jabberSn,
            String msnSn, String mySpaceSn, String skypeSn, String twitterSn,
            String ymSn, String jobTitle, long[] groupIds,
            long[] organizationIds, long[] roleIds,
            List<UserGroupRole> userGroupRoles, long[] userGroupIds,
            ServiceContext serviceContext)
            throws PortalException, SystemException {

        // User

        User user = userPersistence.findByPrimaryKey(userId);
        Company company = companyPersistence.findByPrimaryKey(
                user.getCompanyId());
        String password = oldPassword;
        screenName = getScreenName(screenName);
        emailAddress = emailAddress.trim().toLowerCase();
        openId = openId.trim();
        aimSn = aimSn.trim().toLowerCase();
        facebookSn = facebookSn.trim().toLowerCase();
        icqSn = icqSn.trim().toLowerCase();
        jabberSn = jabberSn.trim().toLowerCase();
        msnSn = msnSn.trim().toLowerCase();
        mySpaceSn = mySpaceSn.trim().toLowerCase();
        skypeSn = skypeSn.trim().toLowerCase();
        twitterSn = twitterSn.trim().toLowerCase();
        ymSn = ymSn.trim().toLowerCase();
        Date now = new Date();

        EmailAddressGenerator emailAddressGenerator =
                EmailAddressGeneratorFactory.getInstance();

        if (emailAddressGenerator.isGenerated(emailAddress)) {
            emailAddress = StringPool.BLANK;
        }

        if (!PropsValues.USERS_EMAIL_ADDRESS_REQUIRED
                && Validator.isNull(emailAddress)) {

            emailAddress = emailAddressGenerator.generate(
                    user.getCompanyId(), userId);
        }

        validate(
                userId, screenName, emailAddress, firstName, middleName, lastName,
                smsSn);

        if (Validator.isNotNull(newPassword1)
                || Validator.isNotNull(newPassword2)) {

            user = updatePassword(
                    userId, newPassword1, newPassword2, passwordReset);

            password = newPassword1;
        }

        user.setModifiedDate(now);

        if (user.getContactId() <= 0) {
            user.setContactId(counterLocalService.increment());
        }

        user.setPasswordReset(passwordReset);

        if (Validator.isNotNull(reminderQueryQuestion)
                && Validator.isNotNull(reminderQueryAnswer)) {

            user.setReminderQueryQuestion(reminderQueryQuestion);
            user.setReminderQueryAnswer(reminderQueryAnswer);
        }

        user.setScreenName(screenName);

        setEmailAddress(
                user, password, firstName, middleName, lastName, emailAddress);

        user.setOpenId(openId);
        user.setLanguageId(languageId);
        user.setTimeZoneId(timeZoneId);
        user.setGreeting(greeting);
        user.setComments(comments);
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setJobTitle(jobTitle);

        userPersistence.update(user, false);

        // Contact

        Date birthday = PortalUtil.getDate(
                birthdayMonth, birthdayDay, birthdayYear,
                new ContactBirthdayException());

        long contactId = user.getContactId();

        Contact contact = contactPersistence.fetchByPrimaryKey(contactId);

        if (contact == null) {
            contact = contactPersistence.create(contactId);

            contact.setCompanyId(user.getCompanyId());
            contact.setUserName(StringPool.BLANK);
            contact.setCreateDate(now);
            contact.setAccountId(company.getAccountId());
            contact.setParentContactId(
                    ContactConstants.DEFAULT_PARENT_CONTACT_ID);
        }

        contact.setModifiedDate(now);
        contact.setFirstName(firstName);
        contact.setMiddleName(middleName);
        contact.setLastName(lastName);
        contact.setPrefixId(prefixId);
        contact.setSuffixId(suffixId);
        contact.setMale(male);
        contact.setBirthday(birthday);
        contact.setSmsSn(smsSn);
        contact.setAimSn(aimSn);
        contact.setFacebookSn(facebookSn);
        contact.setIcqSn(icqSn);
        contact.setJabberSn(jabberSn);
        contact.setMsnSn(msnSn);
        contact.setMySpaceSn(mySpaceSn);
        contact.setSkypeSn(skypeSn);
        contact.setTwitterSn(twitterSn);
        contact.setYmSn(ymSn);
        contact.setJobTitle(jobTitle);

        contactPersistence.update(contact, false);

        // Group

        Group group = groupLocalService.getUserGroup(
                user.getCompanyId(), userId);

        group.setFriendlyURL(StringPool.SLASH + screenName);

        groupPersistence.update(group, false);

        // Groups

        updateGroups(userId, groupIds);

        // Organizations

        updateOrganizations(userId, organizationIds);

        // Roles

        if (roleIds != null) {
            roleIds = EnterpriseAdminUtil.addRequiredRoles(user, roleIds);

            userPersistence.setRoles(userId, roleIds);
        }

        // User group roles

        updateUserGroupRoles(user, groupIds, organizationIds, userGroupRoles);

        // User groups

        if (userGroupIds != null) {
            userGroupLocalService.copyUserGroupLayouts(userGroupIds, userId);

            userPersistence.setUserGroups(userId, userGroupIds);
        }

        // Announcements

        announcementsDeliveryLocalService.getUserDeliveries(user.getUserId());

        // Asset

        if (serviceContext != null) {
            updateAsset(
                    userId, user, serviceContext.getAssetCategoryIds(),
                    serviceContext.getAssetTagNames());
        }

        // Expando

        user.setExpandoBridgeAttributes(serviceContext);

        // Indexer

        Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

        indexer.reindex(user);

        // Permission cache

        PermissionCacheUtil.clearCache();

        return user;
    }

    protected String getScreenName(String screenName) {
        return StringUtil.lowerCase(StringUtil.trim(screenName));
    }

    protected long[] getUserIds(List<User> users) {
        long[] userIds = new long[users.size()];

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            userIds[i] = user.getUserId();
        }

        return userIds;
    }

    protected void sendEmail(User user, String password)
            throws IOException, PortalException, SystemException {

        if (!PrefsPropsUtil.getBoolean(
                user.getCompanyId(),
                PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED)) {

            return;
        }

        long companyId = user.getCompanyId();

        Company company = companyPersistence.findByPrimaryKey(companyId);

        String fromName = PrefsPropsUtil.getString(
                companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
        String fromAddress = PrefsPropsUtil.getString(
                companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

        String toName = user.getFullName();
        String toAddress = user.getEmailAddress();

        String subject = PrefsPropsUtil.getContent(
                companyId, PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT);
        String body = PrefsPropsUtil.getContent(
                companyId, PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY);

        subject = StringUtil.replace(
                subject,
                new String[]{
                    "[$FROM_ADDRESS$]",
                    "[$FROM_NAME$]",
                    "[$PORTAL_URL$]",
                    "[$TO_ADDRESS$]",
                    "[$TO_NAME$]",
                    "[$USER_ID$]",
                    "[$USER_PASSWORD$]",
                    "[$USER_SCREENNAME$]"
                },
                new String[]{
                    fromAddress,
                    fromName,
                    company.getVirtualHost(),
                    toAddress,
                    toName,
                    String.valueOf(user.getUserId()),
                    password,
                    user.getScreenName()
                });

        body = StringUtil.replace(
                body,
                new String[]{
                    "[$FROM_ADDRESS$]",
                    "[$FROM_NAME$]",
                    "[$PORTAL_URL$]",
                    "[$TO_ADDRESS$]",
                    "[$TO_NAME$]",
                    "[$USER_ID$]",
                    "[$USER_PASSWORD$]",
                    "[$USER_SCREENNAME$]"
                },
                new String[]{
                    fromAddress,
                    fromName,
                    company.getVirtualHost(),
                    toAddress,
                    toName,
                    String.valueOf(user.getUserId()),
                    password,
                    user.getScreenName()
                });

        InternetAddress from = new InternetAddress(fromAddress, fromName);

        InternetAddress to = new InternetAddress(toAddress, toName);

        MailMessage message = new MailMessage(from, to, subject, body, true);

        mailService.sendEmail(message);
    }

    protected void setEmailAddress(
            User user, String password, String firstName, String middleName,
            String lastName, String emailAddress)
            throws PortalException, SystemException {

        if (emailAddress.equalsIgnoreCase(user.getEmailAddress())) {
            return;
        }

        long userId = user.getUserId();

        // test@test.com -> test@liferay.com

        if (!user.hasCompanyMx() && user.hasCompanyMx(emailAddress)) {
            mailService.addUser(
                    user.getCompanyId(), userId, password, firstName, middleName,
                    lastName, emailAddress);
        } // test@liferay.com -> bob@liferay.com
        else if (user.hasCompanyMx() && user.hasCompanyMx(emailAddress)) {
            mailService.updateEmailAddress(
                    user.getCompanyId(), userId, emailAddress);
        } // test@liferay.com -> test@test.com
        else if (user.hasCompanyMx() && !user.hasCompanyMx(emailAddress)) {
            mailService.deleteEmailAddress(user.getCompanyId(), userId);
        }

        user.setEmailAddress(emailAddress);
    }

    protected void updateUserGroupRoles(
            User user, long[] groupIds, long[] organizationIds,
            List<UserGroupRole> userGroupRoles)
            throws PortalException, SystemException {

        if (userGroupRoles == null) {
            return;
        }

        List<UserGroupRole> previousUserGroupRoles =
                userGroupRolePersistence.findByUserId(user.getUserId());

        for (UserGroupRole userGroupRole : previousUserGroupRoles) {
            if (userGroupRoles.contains(userGroupRole)) {
                userGroupRoles.remove(userGroupRole);
            } else {
                userGroupRoleLocalService.deleteUserGroupRole(
                        userGroupRole);
            }
        }

        long[] validGroupIds = null;

        if (groupIds != null) {
            validGroupIds = ArrayUtil.clone(groupIds);
        } else {
            validGroupIds = user.getGroupIds();
        }

        if (organizationIds == null) {
            organizationIds = user.getOrganizationIds();
        }

        long[] organizationGroupIds = new long[organizationIds.length];

        for (int i = 0; i < organizationIds.length; i++) {
            long organizationId = organizationIds[i];

            Organization organization =
                    organizationPersistence.findByPrimaryKey(
                    organizationId);

            Group organizationGroup = organization.getGroup();

            organizationGroupIds[i] = organizationGroup.getGroupId();
        }

        validGroupIds = ArrayUtil.append(validGroupIds, organizationGroupIds);

        Arrays.sort(validGroupIds);

        for (UserGroupRole userGroupRole : userGroupRoles) {
            if (Arrays.binarySearch(
                    validGroupIds, userGroupRole.getGroupId()) >= 0) {

                userGroupRoleLocalService.addUserGroupRole(userGroupRole);
            }
        }
    }

    protected void validate(
            long companyId, long userId, boolean autoPassword, String password1,
            String password2, boolean autoScreenName, String screenName,
            String emailAddress, String firstName, String middleName,
            String lastName, long[] organizationIds)
            throws PortalException, SystemException {

        Company company = companyPersistence.findByPrimaryKey(companyId);

        if (company.isSystem()) {
            return;
        }

        if (!autoScreenName) {
            validateScreenName(companyId, userId, screenName);
        }

        if (!autoPassword) {
            PasswordPolicy passwordPolicy =
                    passwordPolicyLocalService.getDefaultPasswordPolicy(companyId);

            PwdToolkitUtil.validate(
                    companyId, 0, password1, password2, passwordPolicy);
        }

        validateEmailAddress(companyId, emailAddress);

        if (Validator.isNotNull(emailAddress)) {
            User user = userPersistence.fetchByC_EA(companyId, emailAddress);

            if (user != null) {
                throw new DuplicateUserEmailAddressException();
            }
        }

        validateFullName(companyId, firstName, middleName, lastName);
    }

    protected void validate(
            long userId, String screenName, String emailAddress,
            String firstName, String middleName, String lastName, String smsSn)
            throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        if (!user.getScreenName().equalsIgnoreCase(screenName)) {
            validateScreenName(user.getCompanyId(), userId, screenName);
        }

        validateEmailAddress(user.getCompanyId(), emailAddress);

        if (!user.isDefaultUser()) {
            if (Validator.isNotNull(emailAddress)
                    && !user.getEmailAddress().equalsIgnoreCase(emailAddress)) {

                if (userPersistence.fetchByC_EA(
                        user.getCompanyId(), emailAddress) != null) {

                    throw new DuplicateUserEmailAddressException();
                }
            }

            validateFullName(
                    user.getCompanyId(), firstName, middleName, lastName);
        }

        if (Validator.isNotNull(smsSn) && !Validator.isEmailAddress(smsSn)) {
            throw new UserSmsException();
        }
    }

    protected void validateEmailAddress(long companyId, String emailAddress)
            throws PortalException, SystemException {

        if (Validator.isNull(emailAddress)
                && !PropsValues.USERS_EMAIL_ADDRESS_REQUIRED) {

            return;
        }

        if (!Validator.isEmailAddress(emailAddress)
                || emailAddress.startsWith("root@")
                || emailAddress.startsWith("postmaster@")) {

            throw new UserEmailAddressException();
        }

        String[] reservedEmailAddresses = PrefsPropsUtil.getStringArray(
                companyId, PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES,
                StringPool.NEW_LINE, PropsValues.ADMIN_RESERVED_EMAIL_ADDRESSES);

        for (int i = 0; i < reservedEmailAddresses.length; i++) {
            if (emailAddress.equalsIgnoreCase(reservedEmailAddresses[i])) {
                throw new ReservedUserEmailAddressException();
            }
        }
    }

    protected void validateFullName(
            long companyId, String firstName, String middleName,
            String lastName)
            throws PortalException {

        if (Validator.isNull(firstName)) {
            throw new ContactFirstNameException();
        } else if (Validator.isNull(lastName)) {
            throw new ContactLastNameException();
        }

        FullNameValidator fullNameValidator =
                FullNameValidatorFactory.getInstance();

        if (!fullNameValidator.validate(
                companyId, firstName, middleName, lastName)) {

            throw new ContactFullNameException();
        }
    }

    protected void validatePassword(
            long companyId, long userId, String password1, String password2)
            throws PortalException, SystemException {

        if (Validator.isNull(password1) || Validator.isNull(password2)) {
            throw new UserPasswordException(
                    UserPasswordException.PASSWORD_INVALID);
        }

        if (!password1.equals(password2)) {
            throw new UserPasswordException(
                    UserPasswordException.PASSWORDS_DO_NOT_MATCH);
        }

        PasswordPolicy passwordPolicy =
                passwordPolicyLocalService.getPasswordPolicyByUserId(userId);

        PwdToolkitUtil.validate(
                companyId, userId, password1, password2, passwordPolicy);
    }

    protected void validateReminderQuery(String question, String answer)
            throws PortalException {

        if (!PropsValues.USERS_REMINDER_QUERIES_ENABLED) {
            return;
        }

        if (Validator.isNull(question)) {
            throw new UserReminderQueryException("Question cannot be null");
        }

        if (Validator.isNull(answer)) {
            throw new UserReminderQueryException("Answer cannot be null");
        }
    }

    protected void validateScreenName(
            long companyId, long userId, String screenName)
            throws PortalException, SystemException {

        if (Validator.isNull(screenName)) {
            throw new UserScreenNameException();
        }

        ScreenNameValidator screenNameValidator =
                ScreenNameValidatorFactory.getInstance();

        if (!screenNameValidator.validate(companyId, screenName)) {
            throw new UserScreenNameException();
        }

        if (Validator.isNumber(screenName)
                && !screenName.equals(String.valueOf(userId))) {

            if (!PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC) {
                throw new UserScreenNameException();
            }

            Group group = groupPersistence.fetchByPrimaryKey(
                    GetterUtil.getLong(screenName));

            if (group != null) {
                throw new UserScreenNameException();
            }
        }

        for (char c : screenName.toCharArray()) {
            if ((!Validator.isChar(c)) && (!Validator.isDigit(c))
                    && (c != CharPool.DASH) && (c != CharPool.PERIOD)
                    && (c != CharPool.UNDERLINE)) {

                throw new UserScreenNameException();
            }
        }

        String[] anonymousNames = PrincipalBean.ANONYMOUS_NAMES;

        for (int i = 0; i < anonymousNames.length; i++) {
            if (screenName.equalsIgnoreCase(anonymousNames[i])) {
                throw new UserScreenNameException();
            }
        }

        User user = userPersistence.fetchByC_SN(companyId, screenName);

        if (user != null) {
            throw new DuplicateUserScreenNameException();
        }

        String friendlyURL = StringPool.SLASH + screenName;

        Group group = groupPersistence.fetchByC_F(companyId, friendlyURL);

        if (group != null) {
            throw new DuplicateUserScreenNameException();
        }

        int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

        if (exceptionType != -1) {
            throw new UserScreenNameException(
                    new GroupFriendlyURLException(exceptionType));
        }

        String[] reservedScreenNames = PrefsPropsUtil.getStringArray(
                companyId, PropsKeys.ADMIN_RESERVED_SCREEN_NAMES,
                StringPool.NEW_LINE, PropsValues.ADMIN_RESERVED_SCREEN_NAMES);

        for (int i = 0; i < reservedScreenNames.length; i++) {
            if (screenName.equalsIgnoreCase(reservedScreenNames[i])) {
                throw new ReservedUserScreenNameException();
            }
        }
    }
    private static Log _log = LogFactoryUtil.getLog(UserLocalServiceImpl.class);
    private static Map<Long, User> _defaultUsers =
            new ConcurrentHashMap<Long, User>();
}
