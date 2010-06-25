package ua.dp.primat.domain;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.LessonDescription;
import ua.dp.primat.domain.lesson.LessonType;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.repositories.StudentGroupRepository;
import ua.dp.primat.schedule.services.LessonService;
import static org.junit.Assert.*;

/**
 *
 * @author fdevelop
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class LessonTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private StudentGroupRepository studentGroupRepository;

    public LessonTest() {
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
    public void testLessonCascadingAdd() {
        final StudentGroup sg = new StudentGroup("PM", Long.valueOf(1), Long.valueOf(2008));
        studentGroupRepository.store(sg);

        final Long semester = Long.valueOf(4);

        final Room room = new Room(Long.valueOf(3), Long.valueOf(31));
        lessonService.saveRoom(room);

        final Cathedra cathedra = new Cathedra("Math.EOM");
        final Discipline discipline = new Discipline("Databases", cathedra);
        lessonService.saveDiscipline(discipline);

        final Lecturer teacher = new Lecturer("Mashenko L. V.", cathedra, LecturerType.SENIORLECTURER);
        lessonService.saveLecturer(teacher);

        final LessonDescription ld = new LessonDescription(discipline, sg, semester, LessonType.LECTURE, teacher, null);
        lessonService.saveLessonDescription(ld);

        final Lesson lesson = new Lesson(Long.valueOf(1), WeekType.BOTH, DayOfWeek.MONDAY, room, ld);
        lessonService.saveLesson(lesson);

        //check the result
        final List<Lesson> resultLessons = lessonService.getLessonsForGroupAndSemester(sg, semester);
        
        System.out.println(String.format("The added lessons (%d): \n", resultLessons.size()));
        for (Lesson l : resultLessons) {
            StringBuilder sb = new StringBuilder();
            sb.append("lesson number: ").append(l.getLessonNumber()).append("\n");
            sb.append("week type: ").append(l.getWeekType()).append("\n");
            sb.append("day: ").append(l.getDayOfWeek()).append("\n");
            sb.append("room: ").append(l.getRoom()).append("\n");
            sb.append("lesson description - discipline: ").append(l.getLessonDescription().getDiscipline().getName()).append("\n");
            sb.append("lesson description - lecturers: ").append(l.getLessonDescription().getLecturerNames()).append("\n");
            sb.append("lesson description - lesson type: ").append(l.getLessonDescription().getLessonType()).append("\n");
            sb.append("lesson description - semester: ").append(l.getLessonDescription().getSemester()).append("\n");
            sb.append("lesson description - group: ").append(l.getLessonDescription().getStudentGroup()).append("\n");
            System.out.println(sb.toString());
        }
        
        int lessonCount = resultLessons.size();
        assertEquals(1, lessonCount);
    }

}*/