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
import org.springframework.beans.factory.annotation.Autowired;
import ua.dp.primat.domain.Cathedra;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.LecturerType;
import ua.dp.primat.domain.Room;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.repositories.CathedraRepository;
import ua.dp.primat.repositories.DisciplineRepository;
import ua.dp.primat.repositories.LecturerRepository;
import ua.dp.primat.repositories.RoomRepository;
import ua.dp.primat.repositories.StudentGroupRepository;
import ua.dp.primat.schedule.admin.groupmanagement.EditGroupPage;
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
        roomRepository.store(new Room(3L, 34L));
        roomRepository.store(new Room(3L, 35L));
        Cathedra cathedra = new Cathedra("RTY");
        disciplineRepository.store(new Discipline("MAT AN", cathedra));
        lecturerRepository.store(new Lecturer("Tonkoshkur", cathedra, LecturerType.ASSIATANT));
        lecturerRepository.store(new Lecturer("Segeda", cathedra, LecturerType.ASSIATANT));
        StudentGroup studentGroup = new StudentGroup("IO", 1L, 2008L);
        groupRepository.store(studentGroup);

        testRendering(AdminHomePage.class);
        testRendering(ManageRooms.class);
        testRendering(ManageDisciplines.class);
        testRendering(ManageLecturersPage.class);
        testRendering(ManageGroupsPage.class);
        testRendering(EditDisciplinePage.class);
        testRendering(EditRoomPage.class);
        testRendering(EditLecturerPage.class);
        testRendering(EditGroupPage.class);

        tester.startPage(new EditSchedulePage(studentGroup, 1L));
    }

    private void testRendering(Class page) {
        tester.startPage(page);
        tester.assertRenderedPage(page);
    }
    WicketTester tester;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    DisciplineRepository disciplineRepository;
    @Autowired
    LecturerRepository lecturerRepository;
    @Autowired
    StudentGroupRepository groupRepository;
}
