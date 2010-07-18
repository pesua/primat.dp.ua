package ua.dp.primat.bigboard;

import org.apache.wicket.protocol.http.WebApplication;

public class WicketApplication extends WebApplication {

    public WicketApplication() {
        super();
    }

    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }
}
