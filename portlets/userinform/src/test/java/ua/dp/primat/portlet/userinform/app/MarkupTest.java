package ua.dp.primat.portlet.userinform.app;

import junit.framework.TestCase;
import org.apache.wicket.PageParameters;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import static org.easymock.EasyMock.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.dp.primat.portlet.userinform.services.LiferayUserService;
import static org.junit.Assert.*;

/**
 *
 * @author fdevelop
 */
public class MarkupTest extends TestCase {

    public MarkupTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testIt() {
        // 1. setup dependencies and mock objects
        LiferayUserService mock = createMock(LiferayUserService.class);

        // 2. setup mock injection environment
        AnnotApplicationContextMock appctx=new AnnotApplicationContextMock();
        appctx.putBean("liferayUserService", mock);

        // 3. run the test
        WicketTester app = new WicketTester();

        // For wicket 1.3.5 use the code below
        app.getApplication().addComponentInstantiationListener(new SpringComponentInjector(app.getApplication(), appctx));

        app.startPage(new HomePage(PageParameters.NULL));
        app.assertRenderedPage(HomePage.class);
    }

    /*@Test
    public void testMarkup() {
        WicketTester tester = new WicketTester(webApp);

        PageParameters pp = new PageParameters("userId=0");
        tester.startPage(HomePage.class, PageParameters.NULL);
        tester.assertRenderedPage(HomePage.class);
    }

    private WebApplication webApp = new WicketApplication() {

        ApplicationContext context = new ClassPathXmlApplicationContext(
			        new String[] {"applicationContext.xml"});

        @Override
        protected void init() {
            mountBookmarkablePage("/view", HomePage.class);
            addComponentInstantiationListener(new SpringComponentInjector(this, context));
        }

    };*/

}