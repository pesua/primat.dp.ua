package ua.dp.primat.schedule.widget;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class Application extends WebApplication {

    @Override
    protected void init() {
        super.init();

        mountBookmarkablePage("/view", ViewPage.class);

        addComponentInstantiationListener(new SpringComponentInjector(this));
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return ViewPage.class;
    }
}
