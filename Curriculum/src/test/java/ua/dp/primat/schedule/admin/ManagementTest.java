/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.primat.schedule.admin;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.dp.primat.schedule.admin.groupmanagement.ManageGroupsPage;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@Transactional
public class ManagementTest {

    public ManagementTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        tester = new WicketTester();
        ApplicationContext appctx = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        tester.getApplication().addComponentInstantiationListener(
                new SpringComponentInjector(tester.getApplication(), appctx));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testManagePages() {
        testRendering(ManageRooms.class);
        testRendering(ManageDisciplines.class);
        testRendering(ManageLecturersPage.class);
        testRendering(ManageGroupsPage.class);
    }

    private void testRendering(Class page) {
        tester.startPage(page);
        tester.assertRenderedPage(page);
    }
    
    WicketTester tester;
}
