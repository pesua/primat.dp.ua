package ua.dp.primat.portlet.userinform.services;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import org.springframework.stereotype.Service;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

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

}
