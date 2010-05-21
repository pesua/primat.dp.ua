package ua.dp.primat.curriculum.view;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import ua.dp.primat.curriculum.data.DataUtils;

public class WicketApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        addComponentInstantiationListener(new SpringComponentInjector(this));
    }

    public final Class<? extends Page> getHomePage() {
        if (DataUtils.isCurriculumsExist()) {
            return HomePage.class;
        } else {
            return NoCurriculumsPage.class;
        }
    }
}
