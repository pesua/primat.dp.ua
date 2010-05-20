package ua.dp.primat.curriculum.view;

import org.apache.wicket.protocol.http.WebApplication;

public class WicketApplication extends WebApplication {

    public WicketApplication() {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public final Class<HomePage> getHomePage() {
        return HomePage.class;
    }
}
