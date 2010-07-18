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
        add(new Label("role", getUserGroups(user, TYPE_ROLES)));
        add(new Label("group", getUserGroups(user, TYPE_GROUP)));
        add(new Label("cath", getUserGroups(user, TYPE_ORG)));

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

    private String getUserGroups(User user, int type) {
        try {
            final StringBuilder groups = new StringBuilder();
            if (type == TYPE_GROUP) {
                for (Group t : user.getGroups()) {
                    groups.append(t.getName());
                    groups.append(VL);
                }
            } else if (type == TYPE_ORG) {
                for (Organization o : user.getOrganizations()) {
                    groups.append(o.getName());
                    groups.append(VL);
                }
            } else if (type == TYPE_ROLES) {
                for (Role r : user.getRoles()) {
                    groups.append(r.getName());
                    groups.append(VL);
                }
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

    private static final int TYPE_GROUP = 0;
    private static final int TYPE_ORG = 1;
    private static final int TYPE_ROLES = 2;

    private static final String MINUS = "-";
    private static final String VL = " | ";
    private static final long serialVersionUID = 1L;
}