package ua.dp.primat.portlet.userinform.app;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.liferay.portal.model.User;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import ua.dp.primat.portlet.userinform.services.LiferayUserService;

public class HomePage extends WebPage {

    /**
     * Constructor for the page, which takes user id from liferay's url.
     *
     * Link example: liferay.com/web/myuser
     *
     * @param parameters - page parameters
     */
    public HomePage(final PageParameters parameters) {
        super(parameters);

        final Request req = RequestCycle.get().getRequest();
        final HttpServletRequest httpreq = ((ServletWebRequest)req).getHttpServletRequest();

        final User lrUser = liferayUserService.getUserInfo(httpreq);
        if (lrUser == null) {
            add(new Label(UD, bundle.getString("label.no.user")));
        } else {
            add(new UserInfoPanel(UD, lrUser));
        }

    }

    private final ResourceBundle bundle = ResourceBundle.getBundle(
            "ua.dp.primat.portlet.userinform.app.HomePage");

    @SpringBean
    private LiferayUserService liferayUserService;

    private static final long serialVersionUID = 1L;
    private static final String UD = "userDetails";
}
