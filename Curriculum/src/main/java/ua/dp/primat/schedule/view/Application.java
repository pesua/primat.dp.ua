package ua.dp.primat.schedule.view;

import javax.portlet.PortletMode;
import org.apache.wicket.Page;
import org.apache.wicket.RequestContext;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.portlet.PortletRequestContext;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import ua.dp.primat.schedule.admin.AdminHomePage;

/**
 * Root point for wicket portlet apllication, that shows schedule.
 * It initializes Spring component injector (init)
 * and shows the wicket page ViewSchedule in getHomePage() method.
 * @author enish, fdevelop
 */
public class Application extends WebApplication {

    @Override
    protected void init() {
        super.init();

        mountBookmarkablePage("/view", ViewHomePage.class);
        mountBookmarkablePage("/edit", AdminHomePage.class);

        addComponentInstantiationListener(new SpringComponentInjector(this));
    }

    @Override
    public Class<? extends Page> getHomePage() {
        final PortletRequestContext prc = (PortletRequestContext)RequestContext.get();
        if (prc.getPortletRequest().getPortletMode().equals(PortletMode.EDIT)) {
            return AdminHomePage.class;
        }
        return ViewHomePage.class;
    }

}
