package ua.dp.primat.portlet.userinform.app;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
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
        add(new Label("group", getUserGroups(user, TYPE_GROUP)));
        add(new Label("cath", getUserGroups(user, TYPE_ORG)));

        Image ava = new Image("avatar");
        ava.add(new SimpleAttributeModifier("src", getAvatarPath(user)));
        add(ava);
    }

    private String getSex(User user) {
        try {
            return user.isMale() ? BUNDLE.getString("sex.male")
                    : BUNDLE.getString("sex.female");
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
            StringBuilder groups = new StringBuilder();
            if (type == TYPE_GROUP) {
                for (Group t : user.getGroups()) {
                    groups.append(t.getName());
                    groups.append(" | ");
                }
            } else if (type == TYPE_ORG) {
                for (Organization o : user.getOrganizations()) {
                    groups.append(o.getName());
                    groups.append(" | ");
                }
            }
            return groups.toString();
        } catch (SystemException se) {
            return "-";
        } catch (PortalException pe) {
            return "-";
        }
    }

    private String getUserBirthday(User user) {
        try {
            return DateFormat.getDateInstance(DateFormat.MEDIUM).format(user.getBirthday());
        } catch (PortalException pe) {
            return "-";
        } catch (SystemException se) {
            return "-";
        }
    }

    private final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "ua.dp.primat.portlet.userinform.app.UserInfoPanel");

    private final int TYPE_GROUP = 0;
    private final int TYPE_ORG = 1;

    private static final long serialVersionUID = 1L;

}
