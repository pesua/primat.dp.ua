package ua.dp.primat.portlet.userinform.app;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.model.impl.UserImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import junit.framework.TestCase;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.MockHttpServletRequest;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import static org.easymock.EasyMock.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.stereotype.Service;
import ua.dp.primat.portlet.userinform.services.LiferayUserService;
import ua.dp.primat.portlet.userinform.services.LiferayUserServiceImpl;

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
    public void testWithUser() {
        // 1. setup dependencies and mock objects       
        //LiferayUserService mock = createMock(LiferayUserService.class);
        LiferayUserServiceMock serviceMock = new LiferayUserServiceMock();

        // 2. setup mock injection environment
        AnnotApplicationContextMock appctx = new AnnotApplicationContextMock();
        appctx.putBean("liferayUserService", serviceMock);

        // 3. run the test
        WicketTester app = new WicketTester();

        // For wicket 1.3.5 use the code below
        app.getApplication().addComponentInstantiationListener(
                new SpringComponentInjector(app.getApplication(), appctx));

        app.startPage(new HomePage(PageParameters.NULL));
        app.assertRenderedPage(HomePage.class);
    }

    @Test
    public void testWithoutUser() {
        // 1. setup dependencies and mock objects
        LiferayUserService mock = createMock(LiferayUserService.class);

        // 2. setup mock injection environment
        AnnotApplicationContextMock appctx = new AnnotApplicationContextMock();
        appctx.putBean("liferayUserService", mock);

        // 3. run the test
        WicketTester app = new WicketTester();

        // For wicket 1.3.5 use the code below
        app.getApplication().addComponentInstantiationListener(
                new SpringComponentInjector(app.getApplication(), appctx));

        app.startPage(new HomePage(PageParameters.NULL));
        app.assertRenderedPage(HomePage.class);
    }

    private class LiferayUserServiceMock implements LiferayUserService {

        public User getUserInfo(HttpServletRequest req) {
            User variousUser = new UserImpl() {

                @Override
                public Date getBirthday() throws PortalException, SystemException {
                    return new Date();
                }

                @Override
                public String getDisplayEmailAddress() {
                    return "mail@com";
                }

                @Override
                public List<Group> getGroups() throws PortalException, SystemException {
                    GroupImpl g = new GroupImpl() {

                        @Override
                        public String getName() {
                            return "group";
                        }

                    };
                    List<Group> mockList = new ArrayList<Group>();
                    mockList.add(g);
                    return mockList;
                }

                @Override
                public List<Organization> getOrganizations() throws PortalException, SystemException {
                    OrganizationImpl g = new OrganizationImpl() {

                        @Override
                        public String getName() {
                            return "org";
                        }

                    };
                    List<Organization> mockList = new ArrayList<Organization>();
                    mockList.add(g);
                    return mockList;
                }

                @Override
                public List<Role> getRoles() throws SystemException {
                    RoleImpl g = new RoleImpl() {

                        @Override
                        public String getName() {
                            return "role";
                        }

                    };
                    List<Role> mockList = new ArrayList<Role>();
                    mockList.add(g);
                    return mockList;
                }

                @Override
                public boolean isMale() throws PortalException, SystemException {
                    return true;
                }

                @Override
                public String getFullName() {
                    return "fullname";
                }
                
            };
            variousUser.setScreenName("screen");
            return variousUser;
        }
    }

}