package ua.dp.primat.portlet.userinform.app;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserServiceUtil;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 * @author fdevelop
 */
public class UserInfoPanel extends Panel {

    public UserInfoPanel(String id, User user) {
        super(id);
        add(new Label("fullname", user.getFullName()));
        add(new Label("email", user.getDisplayEmailAddress()));
        add(new Label("screenname", user.getScreenName()));
        add(new Label("birthday", getUserBirthday(user)));
        add(new Label("group", getUserGroups(user)));
    }

    private String getUserGroups(User user) {
        try {
            StringBuilder groups = new StringBuilder();
            for (UserGroup ug : user.getUserGroups()) {
                groups.append(ug.getName());
                groups.append(" | ");
            }
            return groups.toString();
        } catch (SystemException se) {
            return "-";
        }
    }

    private String getUserBirthday(User user) {
        try {
            return user.getBirthday().toString();
        } catch (PortalException pe) {
            return "-";
        } catch (SystemException se) {
            return "-";
        }
    }

    private static final long serialVersionUID = 1L;

}
