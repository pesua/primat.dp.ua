/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.primat.schedule.widget;

import ua.dp.primat.repositories.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dp.primat.domain.StudentGroup;
import org.junit.Before;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.junit.runner.RunWith;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author EniSh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@Transactional
public class ViewPageTest {

    public ViewPageTest() {
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

    @Test
    public void testSomeMethod() {
        StudentGroup studentGroup = new StudentGroup("IO", 1L, 2008L);
        groupRepository.store(studentGroup);

        tester.startPage(new ViewPage());
        tester.assertRenderedPage(ViewPage.class);
    }
    WicketTester tester;
    @Autowired
    StudentGroupRepository groupRepository;
}
