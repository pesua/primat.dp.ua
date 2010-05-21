package ua.dp.primat.curriculum.view;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import ua.dp.primat.curriculum.data.DataUtils;

public class WicketApplication extends WebApplication {

    public static final String ROOT = "/curriculum/";

    public WicketApplication() {
    }

    public final Class<? extends Page> getHomePage() {
        if (DataUtils.isCurriculumsExist()) {
            return HomePage.class;
        } else {
            return NoCurriculumsPage.class;
        }
    }
}
