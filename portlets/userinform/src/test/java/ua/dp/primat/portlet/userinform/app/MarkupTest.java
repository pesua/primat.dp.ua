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
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
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
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ua.dp.primat.portlet.userinform.services.LiferayUserService;
import ua.dp.primat.portlet.userinform.services.LiferayUserServiceImpl;

/**
 * Test, that checks wicket markup using fake service's implementations.
 * 
 * @author fdevelop
 */
public class MarkupTest extends TestCase {

    public MarkupTest() {
    }

    @Test
    public void testWithUser() {
        // setup mock objects
        ApplicationContext appctx = createAnnotAppCtx(new LiferayUserServiceMock());

        // run the test
        runWicketTest(appctx);
    }

    @Test
    public void testWithUserExceptions() {
        // setup mock objects
        ApplicationContext appctx = createAnnotAppCtx(new LiferayUserServiceSystemExceptions());

        // run the test
        runWicketTest(appctx);
    }

    @Test
    public void testWithUserPortalExceptions() {
        // setup mock objects
        ApplicationContext appctx = createAnnotAppCtx(new LiferayUserServicePortalExceptions());

        // run the test
        runWicketTest(appctx);
    }

    @Test
    public void testWithoutUser() {
        // setup mock objects
        ApplicationContext appctx = createAnnotAppCtx(createMock(LiferayUserService.class));

        // run the test
        runWicketTest(appctx);
    }

    /**
     * Creates application context for testing and puts one service bean for 'liferayUserService'.
     * @param mock - liferayUserService implementation object.
     * @return created app context
     */
    private ApplicationContext createAnnotAppCtx(Object mock) {
        AnnotApplicationContextMock appctx = new AnnotApplicationContextMock();
        appctx.putBean("liferayUserService", mock);
        return appctx;
    }

    /**
     * Run the wicket test and checks the Home page render.
     * @param ac - specified app context
     */
    private void runWicketTest(ApplicationContext ac) {
        WicketTester app = new WicketTester();

        // For wicket 1.3.5 use the code below
        app.getApplication().addComponentInstantiationListener(
                new SpringComponentInjector(app.getApplication(), ac));

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

                @Override
                public ExpandoBridge getExpandoBridge() {
                    return null;
                }
                
            };
            variousUser.setScreenName("screen");
            return variousUser;
        }
    }

    private class LiferayUserServiceSystemExceptions implements LiferayUserService {

        public User getUserInfo(HttpServletRequest req) {
            User variousUser = new UserImpl() {

                @Override
                public Date getBirthday() throws PortalException, SystemException {
                    throw new SystemException("test");
                }

                @Override
                public String getDisplayEmailAddress() {
                    return "mail@com";
                }

                @Override
                public List<Group> getGroups() throws PortalException, SystemException {
                    throw new SystemException("test");
                }

                @Override
                public List<Organization> getOrganizations() throws PortalException, SystemException {
                    throw new SystemException("test");
                }

                @Override
                public List<Role> getRoles() throws SystemException {
                    throw new SystemException("test");
                }

                @Override
                public boolean isMale() throws PortalException, SystemException {
                    throw new SystemException("test");
                }

                @Override
                public String getFullName() {
                    return "fullname";
                }

                @Override
                public ExpandoBridge getExpandoBridge() {
                    return null;
                }

            };
            variousUser.setScreenName("screen");
            return variousUser;
        }
    }

    private class LiferayUserServicePortalExceptions implements LiferayUserService {

        public User getUserInfo(HttpServletRequest req) {
            User variousUser = new UserImpl() {

                @Override
                public Date getBirthday() throws PortalException, SystemException {
                    throw new PortalException("test");
                }

                @Override
                public String getDisplayEmailAddress() {
                    return "mail@com";
                }

                @Override
                public List<Group> getGroups() throws PortalException, SystemException {
                    throw new PortalException("test");
                }

                @Override
                public List<Organization> getOrganizations() throws PortalException, SystemException {
                    throw new PortalException("test");
                }

                @Override
                public List<Role> getRoles() throws SystemException {
                    throw new SystemException("test");
                }

                @Override
                public boolean isMale() throws PortalException, SystemException {
                    throw new PortalException("test");
                }

                @Override
                public String getFullName() {
                    return "fullname";
                }

                @Override
                public ExpandoBridge getExpandoBridge() {
                    return null;
                }

            };
            variousUser.setScreenName("screen");
            return variousUser;
        }
    }

}