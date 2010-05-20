package ua.dp.primat.curriculum.view;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import ua.dp.primat.curriculum.data.DataUtils;

public class WicketApplication extends WebApplication {

    public WicketApplication() {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public final Class<? extends Page> getHomePage() {
        if (DataUtils.isCurriculumsExist())
            return HomePage.class;
        else
            return NoCurriculumsPage.class;
    }
}
