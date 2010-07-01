package ua.dp.primat.portlet.userinform.app;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication
{    
    /**
      * Constructor
      */
    public WicketApplication()
    {
    }

    @Override
    protected final void init() {
        super.init();
        mountBookmarkablePage("/view", HomePage.class);
        addComponentInstantiationListener(new SpringComponentInjector(this));
    }

    public Class<HomePage> getHomePage()
    {
        return HomePage.class;
    }

}