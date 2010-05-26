package ua.dp.primat.curriculum.view;

import javax.portlet.PortletMode;

import org.apache.wicket.Page;
import org.apache.wicket.RequestContext;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.portlet.PortletRequestContext;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.file.Folder;

public class WicketApplication extends WebApplication {

    //ulpoad folder path
    private Folder uploadFolder = null;

    public Folder getUploadFolder() {
        return uploadFolder;
    }

    @Override
    protected final void init() {
        super.init();

        //Curriculum upload folder
        uploadFolder = new Folder(System.getProperty("java.io.tmpdir"), "curriculum-uploads");
	uploadFolder.mkdirs();

        mountBookmarkablePage("/view", HomePage.class);
        mountBookmarkablePage("/edit", EditPage.class);
        addComponentInstantiationListener(new SpringComponentInjector(this));
    }

    public final Class<? extends Page> getHomePage() {
        PortletRequestContext prc = (PortletRequestContext)RequestContext.get();
	if (prc.getPortletRequest().getPortletMode().equals(PortletMode.EDIT))
	{
            return EditPage.class;
	}
        return HomePage.class;
    }
}
