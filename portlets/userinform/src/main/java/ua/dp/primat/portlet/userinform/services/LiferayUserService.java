package ua.dp.primat.portlet.userinform.services;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import org.springframework.stereotype.Service;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author fdevelop
 */
@Service
public class LiferayUserService {
    
    public User getUserInfo(Long uid) {
        try {
            return UserLocalServiceUtil.getUserById(uid);
        } catch (PortalException pe) {
            return null;
        } catch (SystemException se) {
            return null;
        }
    }

    public User getUserInfo(HttpServletRequest req) {
        try {
            ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
		WebKeys.THEME_DISPLAY);

            Group group = GroupLocalServiceUtil.getGroup(
                themeDisplay.getScopeGroupId());

            return (group.isUser() ? UserLocalServiceUtil.
                getUserById(group.getClassPK()) : null);
        } catch (PortalException pe) {
            return null;
        } catch (SystemException se) {
            return null;
        }
    }

}
