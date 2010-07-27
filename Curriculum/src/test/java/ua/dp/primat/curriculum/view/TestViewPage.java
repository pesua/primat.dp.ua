package ua.dp.primat.curriculum.view;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.Cathedra;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.domain.workload.FinalControlType;
import ua.dp.primat.domain.workload.IndividualControl;
import ua.dp.primat.domain.workload.LoadCategory;
import ua.dp.primat.domain.workload.Workload;
import ua.dp.primat.domain.workload.WorkloadType;
import ua.dp.primat.repositories.StudentGroupRepository;
import ua.dp.primat.services.WorkloadService;
import static org.junit.Assert.*;

/**
 * Test for curriculum portlet, which checks the markup of view/edit/no-curriculum pages.
 * Also, storaging a fake data using spring WorkloadService.
 * @author fdevelop
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
public class TestViewPage {

    public TestViewPage() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testNoView() {
        WicketTester tester = new WicketTester(new TestWicketApplication() {

            @Override
            public Class<? extends Page> getHomePage() {
                return HomePage.class;
            }

        });

        tester.startPage(HomePage.class);
        tester.assertRenderedPage(NoCurriculumsPage.class);
    }

    @Test
    public void testSimpleView() {
        WicketTester tester = new WicketTester(new TestWicketApplication() {

            @Override
            public Class<? extends Page> getHomePage() {
                return HomePage.class;
            }

        });

        prepareFakeData();
        tester.startPage(HomePage.class);
        tester.assertRenderedPage(HomePage.class);
    }

    @Test
    public void testSimpleEdit() {
        WicketTester tester = new WicketTester(new TestWicketApplication() {

            @Override
            public Class<? extends Page> getHomePage() {
                return EditPage.class;
            }

        });

        tester.startPage(EditPage.class);
        tester.assertRenderedPage(EditPage.class);

        /*
        FormTester formUploadXLS = tester.newFormTester("formUploadXLS");
        formUploadXLS.setValue("parseSheet", "0");
        formUploadXLS.submit();

        tester.assertNoInfoMessage();*/
    }

    private void prepareFakeData() {
        //commit workloads
        final List<Workload> workloads = new ArrayList<Workload>();

        final StudentGroup group = new StudentGroup("CC", Long.valueOf(1), Long.valueOf(2010));
        final Workload workload = new Workload();
        workload.setDiscipline(new Discipline("name", new Cathedra("cath")));
        workload.setLoadCategory(LoadCategory.Normative);
        workload.setType(WorkloadType.wtHumanities);
        workload.setStudentGroup(group);
        workload.setCourseWork(true);
        workload.setFinalControlType(FinalControlType.Exam);
        workload.setSelfworkHours(Long.valueOf(777));
        workload.setLaboratoryHours(Long.valueOf(2));
        workload.setLectionHours(Long.valueOf(8));
        workload.setPracticeHours(Long.valueOf(9));
        workload.setSemesterNumber(Long.valueOf(1));
        //individual controls
        workload.getIndividualControl().add(new IndividualControl("KR", Long.valueOf(17)));
        //add to the result
        workloads.add(workload);

        workloadService.storeWorkloads(workloads);
    }

    private class TestWicketApplication extends WicketApplication {
        @Override
        protected void addSpringComponentInjector() {
            ApplicationContext appctx = new ClassPathXmlApplicationContext("applicationContext-test.xml");
            addComponentInstantiationListener(new SpringComponentInjector(this, appctx));
        }
    }

    @Autowired
    private WorkloadService workloadService;

}