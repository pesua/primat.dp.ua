<%
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
%>

<%@ include file="/html/portlet/login/init.jsp" %>
<%@ include file="/html/portlet/schoolfield/init.jsp" %>

<script type=text/javascript language=JavaScript>
function showRolePanel()
{
	var objSelect = document.getElementById("roleSelect");
	var pnl = objSelect.options[objSelect.selectedIndex].value;
	if (pnl == 0) {
		document.getElementById('panel_student').style.display='';
		document.getElementById('panel_company').style.display='none';
	} else if (pnl == 1) {
		document.getElementById('panel_student').style.display='none';
		document.getElementById('panel_company').style.display='';
	}
}

function showNoGroup(nogroup)
{
  if (nogroup == true) {
    document.getElementById('student_select_group').style.display='none';
		document.getElementById('student_request_group').style.display='';
		document.getElementById('studentGroupRequest').value = document.getElementById('student-new-group-request').value;
		if (document.getElementById('studentGroupRequest').value == '') {
      document.getElementById('studentGroupRequest').value = '-';
		}
  } else {
    document.getElementById('student_select_group').style.display='';
		document.getElementById('student_request_group').style.display='none';
		document.getElementById('studentGroupRequest').value = '';
  }
}
</script>

<style type=text/css>
  #roleSelectArea {
  	background-color: #E4F3F7;
  	border: none;
	  margin: 20px 0;
	  padding: 20px 16px;
  	border-top: 1px solid #CDDCDE;
	  border-bottom: 1px solid #CDDCDE;
	  width: 400px;
  }
  
  #roleSelectArea .chooser {
  	padding-bottom: 5px;
  }
</style>

<%
String redirect = ParamUtil.getString(request, "redirect");

String openId = ParamUtil.getString(request, "openId");

User user2 = null;
Contact contact2 = null;

PasswordPolicy passwordPolicy = PasswordPolicyLocalServiceUtil.getDefaultPasswordPolicy(company.getCompanyId());

Calendar birthday = CalendarFactoryUtil.getCalendar();

birthday.set(Calendar.MONTH, Calendar.JANUARY);
birthday.set(Calendar.DATE, 1);
birthday.set(Calendar.YEAR, 1970);

boolean male = BeanParamUtil.getBoolean(contact2, request, "male", true);
%>

<portlet:actionURL var="createAccoutURL">
	<portlet:param name="saveLastPath" value="0" />
	<portlet:param name="struts_action" value="/login/create_account" />
</portlet:actionURL>

<aui:form action="<%= createAccoutURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="openId" type="hidden" value="<%= openId %>" />

	<liferay-ui:error exception="<%= AddressCityException.class %>" message="please-enter-a-valid-city" />
	<liferay-ui:error exception="<%= AddressStreetException.class %>" message="please-enter-a-valid-street" />
	<liferay-ui:error exception="<%= AddressZipException.class %>" message="please-enter-a-valid-zip" />
	<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
	<liferay-ui:error exception="<%= ContactFirstNameException.class %>" message="please-enter-a-valid-first-name" />
	<liferay-ui:error exception="<%= ContactFullNameException.class %>" message="please-enter-a-valid-first-middle-and-last-name" />
	<liferay-ui:error exception="<%= ContactLastNameException.class %>" message="please-enter-a-valid-last-name" />
	<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" message="the-email-address-you-requested-is-already-taken" />
	<liferay-ui:error exception="<%= DuplicateUserIdException.class %>" message="the-user-id-you-requested-is-already-taken" />
	<liferay-ui:error exception="<%= DuplicateUserScreenNameException.class %>" message="the-screen-name-you-requested-is-already-taken" />
	<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />
	<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />
	<liferay-ui:error exception="<%= NoSuchListTypeException.class %>" message="please-select-a-type" />
	<liferay-ui:error exception="<%= NoSuchRegionException.class %>" message="please-select-a-region" />
	<liferay-ui:error exception="<%= PhoneNumberException.class %>" message="please-enter-a-valid-phone-number" />
	<liferay-ui:error exception="<%= RequiredFieldException.class %>" message="please-fill-out-all-required-fields" />
	<liferay-ui:error exception="<%= ReservedUserEmailAddressException.class %>" message="the-email-address-you-requested-is-reserved" />
	<liferay-ui:error exception="<%= ReservedUserIdException.class %>" message="the-user-id-you-requested-is-reserved" />
	<liferay-ui:error exception="<%= ReservedUserScreenNameException.class %>" message="the-screen-name-you-requested-is-reserved" />
	<liferay-ui:error exception="<%= TermsOfUseException.class %>" message="you-must-agree-to-the-terms-of-use" />
	<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
	<liferay-ui:error exception="<%= UserIdException.class %>" message="please-enter-a-valid-user-id" />

	<liferay-ui:error exception="<%= UserPasswordException.class %>">

		<%
		UserPasswordException upe = (UserPasswordException)errorException;
		%>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_CONTAINS_TRIVIAL_WORDS %>">
			<liferay-ui:message key="that-password-uses-common-words-please-enter-in-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters" />
		</c:if>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_INVALID %>">
			<liferay-ui:message key="that-password-is-invalid-please-enter-in-a-different-password" />
		</c:if>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_LENGTH %>">
			<%= LanguageUtil.format(pageContext, "that-password-is-too-short-or-too-long-please-make-sure-your-password-is-between-x-and-512-characters", String.valueOf(passwordPolicy.getMinLength()), false) %>
		</c:if>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORDS_DO_NOT_MATCH %>">
			<liferay-ui:message key="the-passwords-you-entered-do-not-match-each-other-please-re-enter-your-password" />
		</c:if>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="please-enter-a-valid-screen-name" />
	<liferay-ui:error exception="<%= WebsiteURLException.class %>" message="please-enter-a-valid-url" />

	<c:if test='<%= SessionMessages.contains(request, "missingOpenIdUserInformation") %>'>
		<div class="portlet-msg-info">
			<liferay-ui:message key="you-have-successfully-authenticated-please-provide-the-following-required-information-to-access-the-portal" />
		</div>
	</c:if>

	<aui:model-context bean="<%= contact2 %>" model="<%= Contact.class %>" />

	<aui:fieldset>
		<aui:column>
			<aui:input name="firstName" />

			<aui:input name="middleName" />

			<aui:input name="lastName" />
			
		</aui:column>
		<aui:column>
			<c:if test="<%= !PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE) %>">
				<aui:input bean="<%= user2 %>" model="<%= User.class %>" name="screenName" />
			</c:if>
			
			<c:choose>
				<c:when test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY) %>">
					<aui:input name="birthday" value="<%= birthday %>" />
				</c:when>
				<c:otherwise>
					<aui:input name="birthdayMonth" type="hidden" value="<%= Calendar.JANUARY %>" />
					<aui:input name="birthdayDay" type="hidden" value="1" />
					<aui:input name="birthdayYear" type="hidden" value="1990" />
				</c:otherwise>
			</c:choose>

			<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE) %>">
				<aui:select label="gender" name="male">
					<aui:option label="male" value="1" />
					<aui:option label="female" selected="<%= !male %>" value="0" />
				</aui:select>
			</c:if>

		</aui:column>
	</aui:fieldset>
	      
	<aui:fieldset>
		<aui:column>
      <span class="aui-field">
        <span class="aui-field-content">
          <liferay-ui:custom-attribute
            className="com.liferay.portal.model.User" classPK="<%= 0 %>"
            editable="<%= true %>" label="<%= true %>"
            name="school-school"
          />
        </span>
      </span>
		</aui:column>
		<aui:column>
      <span class="aui-field">
        <span class="aui-field-content">
          <liferay-ui:custom-attribute
            className="com.liferay.portal.model.User" classPK="<%= 0 %>"
            editable="<%= true %>" label="<%= true %>"
            name="school-country"
          />
        </span>
      </span>
      <span class="aui-field">
        <span class="aui-field-content">
          <liferay-ui:custom-attribute
            className="com.liferay.portal.model.User" classPK="<%= 0 %>"
            editable="<%= true %>" label="<%= true %>"
            name="school-city"
          />
        </span>
      </span>
		</aui:column>
	</aui:fieldset>
	
	<%@ include file="/html/portlet/schoolfield/popup.jsp" %>
	
	<aui:fieldset>
		<aui:column>
      <aui:input bean="<%= user2 %>" model="<%= User.class %>" name="emailAddress" />

		</aui:column>
		<aui:column>
			
			<c:if test="<%= PropsValues.LOGIN_CREATE_ACCOUNT_ALLOW_CUSTOM_PASSWORD %>">
				<aui:input label="password" name="password1" size="30" type="password" value="" />

				<aui:input label="enter-again" name="password2" size="30" type="password" value="" />
			</c:if>
			
		</aui:column>
	</aui:fieldset>
	
	<div id="roleSelectArea">
	<aui:fieldset>
		<aui:column>      
      <!--<div class="chooser">
      <strong><liferay-ui:message key="role-select" />&nbsp;</strong>
      <select name="roleSelect" id="roleSelect" onChange=showRolePanel()>
        <option selected value="0"><liferay-ui:message key="role-select-student" /></option>
        <option value="1"><liferay-ui:message key="role-select-company" /></option>
      </select>
      </div>-->
      <hr/>
      <div id="panel_student">
        <input name="studentGroupRequest" id="studentGroupRequest" type="hidden" value=""/>
        <div id="student_select_group">
          <aui:select label="student-groups" name="student-groups">
            <%
            List groups = GroupLocalServiceUtil.getGroups(0,GroupLocalServiceUtil.getGroupsCount());
            for (int i = 0; i < groups.size(); i++)
            {
                Group group = (Group)groups.get(i);
                if(group.isCommunity() && (!group.getName().equals("Control Panel"))
                    && (!group.getName().equals("Guest")) && (group.getName().length() > 0))
                {
                    %>
                    <aui:option label="<%= group.getName() %>" selected="false" value="<%= group.getGroupId() %>" />
                    <%
                }
            }
            %>
          </aui:select>
          <a onClick="showNoGroup(true)"><liferay-ui:message key="student-no-group"/></a>
        </div>
        <div id="student_request_group" style="display: none;">
          <span class="aui-field">
            <span class="aui-field-content">
              <liferay-ui:custom-attribute
                className="com.liferay.portal.model.User" classPK="<%= 0 %>"
                editable="<%= true %>" label="<%= true %>"
                name="student-new-group-request"
              />
            </span>
          </span>
          <a onClick="showNoGroup(false)"><liferay-ui:message key="student-group-select"/></a>
        </div>
        <aui:input label="is-member-student-council" name="is-member-student-council" type="checkbox"/>
      </div>

      <div id="panel_company" style="display: none;">
        <span class="aui-field">
          <span class="aui-field-content">
            <liferay-ui:custom-attribute
              className="com.liferay.portal.model.User" classPK="<%= 0 %>"
              editable="<%= true %>" label="<%= true %>"
              name="company-name"
            />
          </span>
        </span>
        <span class="aui-field">
          <span class="aui-field-content">
            <liferay-ui:custom-attribute
              className="com.liferay.portal.model.User" classPK="<%= 0 %>"
              editable="<%= true %>" label="<%= true %>"
              name="company-activity"
            />
          </span>
        </span>
        <br/>
        <span class="aui-field">
          <span class="aui-field-content">
            <liferay-ui:custom-attribute
              className="com.liferay.portal.model.User" classPK="<%= 0 %>"
              editable="<%= true %>" label="<%= true %>"
              name="company-phone"
            />
          </span>
        </span>
        
        <span class="aui-field">
          <span class="aui-field-content">
            <liferay-ui:custom-attribute
              className="com.liferay.portal.model.User" classPK="<%= 0 %>"
              editable="<%= true %>" label="<%= true %>"
              name="company-address"
            />
          </span>
        </span>
        
        <span class="aui-field">
          <span class="aui-field-content">
            <liferay-ui:custom-attribute
              className="com.liferay.portal.model.User" classPK="<%= 0 %>"
              editable="<%= true %>" label="<%= true %>"
              name="company-mail"
            />
          </span>
        </span>
        
        <span class="aui-field">
          <span class="aui-field-content">
            <liferay-ui:custom-attribute
              className="com.liferay.portal.model.User" classPK="<%= 0 %>"
              editable="<%= true %>" label="<%= true %>"
              name="company-web"
            />
          </span>
        </span>
      </div>
    </aui:column>
	</aui:fieldset>
	</div>
	
	<aui:fieldset>
		<aui:column>
			<c:if test="<%= PropsValues.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT %>">
				<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
					<portlet:param name="struts_action" value="/login/captcha" />
				</portlet:actionURL>

				<liferay-ui:captcha url="<%= captchaURL %>" />
			</c:if>
		</aui:column>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<liferay-util:include page="/html/portlet/login/navigation.jsp" />

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />firstName);
	</aui:script>
</c:if>
