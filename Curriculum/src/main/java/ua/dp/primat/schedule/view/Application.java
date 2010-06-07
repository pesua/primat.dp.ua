package ua.dp.primat.schedule.view;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Root point for wicket portlet apllication, that shows schedule.
 * It initializes Spring component injector (init)
 * and shows the wicket page ViewSchedule in getHomePage() method.
 * @author fdevelop
 */
public class Application extends WebApplication {

    @Override
    protected void init() {
        super.init();
        addComponentInstantiationListener(new SpringComponentInjector(this));
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return ViewSchedule.class;
    }

}
