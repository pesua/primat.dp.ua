package ua.dp.primat.portlet.userinform.app;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import java.text.DateFormat;
import java.util.ResourceBundle;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 * @author fdevelop
 */
public class UserInfoPanel extends Panel {

    public UserInfoPanel(String id, User user) {
        super(id);
        add(new Label("fullname", user.getFullName()));
        add(new Label("sex", getSex(user)));
        add(new Label("email", user.getDisplayEmailAddress()));
        add(new Label("screenname", user.getScreenName()));
        add(new Label("birthday", getUserBirthday(user)));
        add(new Label("role", getUserGroups(user)));
        add(new Label("group", getUserRoles(user)));
        add(new Label("cath", getUserOrganization(user)));

        final Image ava = new Image("avatar");
        ava.add(new SimpleAttributeModifier("src", getAvatarPath(user)));
        add(ava);
    }

    private String getSex(User user) {
        try {
            return user.isMale() ? bundle.getString("sex.male")
                    : bundle.getString("sex.female");
        } catch (SystemException se) {
            return "";
        } catch (PortalException pe) {
            return "";
        }
    }

    private String getAvatarPath(User user) {
        return String.format("/image/user_%s_portrait?img_id=%d", "male",
                             user.getPortraitId());
    }

    private String getUserGroups(User user) {
        try {
            final StringBuilder groups = new StringBuilder();
            for (Group t : user.getGroups()) {
                if (groups.length() > 0) {
                    groups.append(COMA);
                }
                groups.append(t.getName());
            }
            return groups.toString();
        } catch (SystemException se) {
            return MINUS;
        } catch (PortalException pe) {
            return MINUS;
        }
    }

    private String getUserRoles(User user) {
        try {
            final StringBuilder groups = new StringBuilder();

            for (Role r : user.getRoles()) {
                if (groups.length() > 0) {
                    groups.append(COMA);
                }
                groups.append(r.getName());
            }
            return groups.toString();
        } catch (SystemException se) {
            return MINUS;
        }
    }

    private String getUserOrganization(User user) {
        try {
            final StringBuilder groups = new StringBuilder();
            for (Organization o : user.getOrganizations()) {
                if (groups.length() > 0) {
                    groups.append(COMA);
                }
                groups.append(o.getName());
            }
            return groups.toString();
        } catch (SystemException se) {
            return MINUS;
        } catch (PortalException pe) {
            return MINUS;
        }
    }

    private String getUserBirthday(User user) {
        try {
            return DateFormat.getDateInstance(DateFormat.MEDIUM).format(user.getBirthday());
        } catch (PortalException pe) {
            return MINUS;
        } catch (SystemException se) {
            return MINUS;
        }
    }
    private final ResourceBundle bundle = ResourceBundle.getBundle(
            "ua.dp.primat.portlet.userinform.app.UserInfoPanel");
    private static final String MINUS = "-";
    private static final String COMA = ", ";
    private static final long serialVersionUID = 1L;
}
