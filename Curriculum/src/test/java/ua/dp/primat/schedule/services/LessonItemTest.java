/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.services;

import org.junit.Test;
import ua.dp.primat.domain.Cathedra;
import ua.dp.primat.domain.lesson.DayOfWeek;
import static org.junit.Assert.*;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.LessonDescription;
import ua.dp.primat.domain.lesson.LessonType;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.domain.workload.Discipline;

/**
 *
 * @author work
 */
public class LessonItemTest {

    public LessonItemTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.junit.Test
    public void testIsOneLesson() {
        System.out.println("isOneLesson");
        LessonItem instance = new LessonItem();
        instance.setOneLesson();
        boolean expResult = true;
        boolean result = instance.isOneLesson();
        assertEquals("WA. must be one lesson", expResult, result);


        instance.setTwoLesson();
        expResult = false;
        result = instance.isOneLesson();
        assertEquals("WA. must be two lessons", expResult, result);
    }

//    @org.junit.Test
//    public void testGetDenominator() {
//        System.out.println("getDenominator");
//        LessonItem instance = new LessonItem();
//        EditableLesson expResult = null;
//        EditableLesson result = instance.getDenominator();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    @org.junit.Test
//    public void testSetDenominator() {
//        System.out.println("setDenominator");
//        EditableLesson denominator = null;
//        LessonItem instance = new LessonItem();
//        instance.setDenominator(denominator);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    @org.junit.Test
//    public void testGetNumerator() {
//        System.out.println("getNumerator");
//        LessonItem instance = new LessonItem();
//        EditableLesson expResult = null;
//        EditableLesson result = instance.getNumerator();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    @org.junit.Test
//    public void testSetNumerator() {
//        System.out.println("setNumerator");
//        EditableLesson numerator = null;
//        LessonItem instance = new LessonItem();
//        instance.setNumerator(numerator);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    @org.junit.Test
    public void testMergeWithLesson() {
        System.out.println("mergeWithLesson");
        LessonItem instance = new LessonItem();
        assertTrue("", instance.getNumerator().isEmpty());
        assertTrue("", instance.getDenominator().isEmpty());
        Discipline discipline = new Discipline("trololo", new Cathedra("1"));
        LessonDescription lessonDescription = new LessonDescription(discipline, null, Long.MIN_VALUE, LessonType.LECTURE, null, null);
        Lesson lesson = new Lesson(1L, WeekType.DENOMINATOR, DayOfWeek.MONDAY, null,lessonDescription);
        instance.mergeWithLesson(lesson);
        assertEquals("Disciplines is equals.",instance.getDenominator().getDiscipline(), discipline);
        
    }

}