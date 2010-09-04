/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.view;

import org.apache.wicket.Page;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
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
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.curriculum.view.WicketApplication;
import ua.dp.primat.domain.Cathedra;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.LecturerType;
import ua.dp.primat.domain.Room;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.LessonDescription;
import ua.dp.primat.domain.lesson.LessonType;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.repositories.DisciplineRepository;
import ua.dp.primat.repositories.LecturerRepository;
import ua.dp.primat.repositories.LessonDescriptionRepository;
import ua.dp.primat.repositories.LessonRepository;
import ua.dp.primat.repositories.RoomRepository;
import ua.dp.primat.repositories.StudentGroupRepository;

/**
 *
 * @author fdevelop
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
public class ScheduleMarkupTest {

    public ScheduleMarkupTest() {
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
    public void testStandard() {
        WicketTester tester = new WicketTester(new TestWicketApplication() {

            @Override
            public Class<? extends Page> getHomePage() {
                return ViewHomePage.class;
            }

        });

        prepareFakeData();
        tester.startPage(ViewHomePage.class);
        tester.assertRenderedPage(ViewHomePage.class);

        tester.startPage(LecturerSchedulePage.class);
        tester.assertRenderedPage(LecturerSchedulePage.class);

        tester.startPage(RoomSchedulePage.class);
        tester.assertRenderedPage(RoomSchedulePage.class);
    }

    private void prepareFakeData() {
        //commit test data
        final StudentGroup sg = new StudentGroup("PP", Long.valueOf(1), Long.valueOf(2009));
        groupRepository.store(sg);
        final Cathedra c = new Cathedra("cath");
        final Discipline d = new Discipline("discipline", c);
        disciplineRepository.store(d);

        final Lecturer l = new Lecturer("name", c, LecturerType.ASSIATANT);
        lecturerRepository.store(l);
        final Room r = new Room(Long.valueOf(1), Long.valueOf(2));
        roomRepository.store(r);
        final LessonDescription ld = new LessonDescription(d, sg, Long.valueOf(1), LessonType.LECTURE, l, null);
        descriptionRepository.store(ld);
        final Lesson les = new Lesson(Long.valueOf(1), WeekType.BOTH, DayOfWeek.MONDAY, r, ld);
        lessonRepository.store(les);
    }

    private class TestWicketApplication extends WicketApplication {
        @Override
        protected void addSpringComponentInjector() {
            ApplicationContext appctx = new ClassPathXmlApplicationContext("applicationContext-test.xml");
            addComponentInstantiationListener(new SpringComponentInjector(this, appctx));
        }
    }

    @Autowired
    private StudentGroupRepository groupRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private LessonDescriptionRepository descriptionRepository;

    @Autowired
    private LessonRepository lessonRepository;

}