/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.services;

import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.LessonDescription;
import ua.dp.primat.domain.lesson.LessonType;
import ua.dp.primat.domain.lesson.WeekType;
import static org.junit.Assert.*;

/**
 *
 * @author work
 */
public class WeekLessonColectionTest {

    public WeekLessonColectionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetDayList() {
        System.out.println("WeekLessonColection");

        List<Lesson> l = new ArrayList<Lesson>();
        LessonDescription ld = new LessonDescription(null, null, 1L, LessonType.LECTURE, null, null);
        Lesson less = new Lesson(1L, WeekType.BOTH, DayOfWeek.MONDAY, null, ld);
        less.setId(1L);
        l.add(less);

        WeekLessonColection instance = new WeekLessonColection(l);
        List<LessonItem[]> result = instance.getDayList();
        assertEquals("", Long.valueOf(1), result.get(0)[0].getNumerator().getId());
    }

//    @Test
//    public void testGetLessonItems() {
//        System.out.println("getLessonItems");
//        WeekLessonColection instance = new WeekLessonColection();
//        LessonItem[][] expResult = null;
//        LessonItem[][] result = instance.getLessonItems();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}