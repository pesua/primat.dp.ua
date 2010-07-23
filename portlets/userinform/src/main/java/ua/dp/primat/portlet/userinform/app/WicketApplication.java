package ua.dp.primat.portlet.userinform.app;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends WebApplication{    

    public WicketApplication() {
        super();
    }

    @Override
    protected void init() {
        super.init();
        mountBookmarkablePage("/view", HomePage.class);
        addComponentInstantiationListener(new SpringComponentInjector(this));
    }

    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

}