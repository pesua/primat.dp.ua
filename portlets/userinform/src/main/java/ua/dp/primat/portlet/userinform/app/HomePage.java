package ua.dp.primat.portlet.userinform.app;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.liferay.portal.model.User;
import java.util.ResourceBundle;
import ua.dp.primat.portlet.userinform.services.LiferayUserService;

/**
 * Homepage.
 */
public class HomePage extends WebPage {

    /**
     * Constructor for the page, which takes user id and info from
     * page parameter or shows no-user-page.
     *
     * Link example: http://localhost/.../pagename?p_p_id
     *      =userinform_WAR_userinform&userId=11331
     *
     * @param parameters - page parameters
     */
    public HomePage(final PageParameters parameters) {
        super(parameters);
        
        final User lrUser = userService.getUserInfo(parameters.getAsLong(
                "userId", -1));
        if (lrUser == null) {
            add(new Label("userDetails", bundle.getString("label.no.user")));
        } else {
            add(new UserInfoPanel("userDetails", lrUser));
        }

    }

    private final ResourceBundle bundle = ResourceBundle.getBundle(
            "ua.dp.primat.portlet.userinform.app.HomePage");

    @SpringBean
    private LiferayUserService userService;

    private static final long serialVersionUID = 1L;
}
