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
            final ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(
                    WebKeys.THEME_DISPLAY);

            final Group group = GroupLocalServiceUtil.getGroup(
                    themeDisplay.getScopeGroupId());

            if (group.isUser()) {
                return UserLocalServiceUtil.getUserById(group.getClassPK());
            } else
            {
                final User user = null;
                return user;
            }
        } catch (PortalException pe) {
            return null;
        } catch (SystemException se) {
            return null;
        }
    }
}
