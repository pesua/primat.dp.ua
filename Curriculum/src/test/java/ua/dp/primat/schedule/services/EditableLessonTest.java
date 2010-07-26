package ua.dp.primat.schedule.services;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.dp.primat.domain.Cathedra;
import static org.junit.Assert.*;
import org.junit.Before;
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

public class EditableLessonTest {

    public EditableLessonTest() {
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

    /**
     * Test of fromLesson method, of class EditableLesson.
     */
    @Test
    public void testFromLesson() {
        System.out.println("fromLesson");
        Lecturer testLecturer = new Lecturer("username", null, LecturerType.PROFESSOR);
        Discipline testDisciple = new Discipline("www", new Cathedra());
        Room testRoom = new Room(3L, 23L);
        LessonDescription ld = new LessonDescription(testDisciple, new StudentGroup("PZ", 1L, 2008L), 1L, LessonType.LECTURE, testLecturer , null);
        Lesson lesson = new Lesson(1L, WeekType.DENOMINATOR, DayOfWeek.THURSDAY, testRoom, ld);
        lesson.setId(1L);

        EditableLesson result = EditableLesson.fromLesson(lesson);
        assertEquals("Wrong id", Long.valueOf(1L), result.getId());
        assertEquals("Wrong assistant", null, result.getAsistant());
        assertEquals("Wrong lecturer", testLecturer, result.getLecturer());
        assertEquals("Wrong discipline", testDisciple, result.getDiscipline());
        assertEquals("Wrong week type", WeekType.DENOMINATOR, result.getWeekType());
        assertEquals("Wrong room", testRoom, result.getRoom());
        assertEquals("Wrong lesson type", LessonType.LECTURE, result.getLessonType());
    }

    /**
     * Test of isEmpty method, of class EditableLesson.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        EditableLesson instance = new EditableLesson();
        instance.setDiscipline(new Discipline("www", new Cathedra()));
        assertFalse("WA. Must be not empty", instance.isEmpty());
        instance.setDiscipline(null);
        assertTrue("WA. Must be empty", instance.isEmpty());
    }

    /**
     * Test of toLesson method, of class EditableLesson.
     */
    @Test
    public void testToLesson() {
        System.out.println("toLesson");
        DayOfWeek day = DayOfWeek.THURSDAY;
        Long lessonNum = 1L;
        EditableLesson instance = new EditableLesson();
        instance.setDiscipline(new Discipline("www", new Cathedra()));
        instance.setAsistant(null);
        instance.setLecturer(new Lecturer("username", null, LecturerType.PROFESSOR));
        instance.setId(1L);
        instance.setLessonType(LessonType.LECTURE);
        instance.setRoom(new Room(3L, 23L));
        instance.setWeekType(WeekType.DENOMINATOR);
        Lesson result = instance.toLesson(day, lessonNum);

        assertEquals("Wrong id", Long.valueOf(1L), result.getId());
        assertEquals("Wrong assistant", null, result.getLessonDescription().getAssistant());
        assertEquals("Wrong lecturer", instance.getLecturer(), result.getLessonDescription().getLecturer());
        assertEquals("Wrong discipline", instance.getDiscipline(), result.getLessonDescription().getDiscipline());
        assertEquals("Wrong week type", WeekType.DENOMINATOR, result.getWeekType());
        assertEquals("Wrong room", instance.getRoom(), result.getRoom());
        assertEquals("Wrong lesson type", LessonType.LECTURE, result.getLessonDescription().getLessonType());
    }

}