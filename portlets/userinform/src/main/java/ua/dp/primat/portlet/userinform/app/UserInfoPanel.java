package ua.dp.primat.portlet.userinform.app;

import com.liferay.portal.model.User;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 * @author fdevelop
 */
public class UserInfoPanel extends Panel {

    public UserInfoPanel(String id, User user) {
        super(id);
        add(new Label("fullName", user.getFullName()));
    }

    private static final long serialVersionUID = 1L;

}
