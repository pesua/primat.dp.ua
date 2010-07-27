package ua.dp.primat.curriculum.view;

import javax.portlet.PortletMode;

import org.apache.wicket.Page;
import org.apache.wicket.RequestContext;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.portlet.PortletRequestContext;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.file.Folder;

public class WicketApplication extends WebApplication {

    public Folder getUploadFolder() {
        return uploadFolder;
    }

    public Class<? extends Page> getHomePage() {
        final PortletRequestContext prc = (PortletRequestContext)RequestContext.get();
        if (prc.getPortletRequest().getPortletMode().equals(PortletMode.EDIT)) {
            return EditPage.class;
        }
        return HomePage.class;
    }

    @Override
    protected final void init() {
        super.init();

        //Curriculum upload folder
        uploadFolder = new Folder(System.getProperty("java.io.tmpdir"), "curriculum-uploads");
        uploadFolder.mkdirs();

        mountBookmarkablePage("/view", HomePage.class);
        mountBookmarkablePage("/edit", EditPage.class);
        addSpringComponentInjector();
    }

    protected void addSpringComponentInjector() {
        addComponentInstantiationListener(new SpringComponentInjector(this));
    }

    //upload folder path
    private Folder uploadFolder = null;
}
