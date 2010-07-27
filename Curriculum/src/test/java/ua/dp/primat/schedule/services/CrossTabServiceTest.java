/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.primat.schedule.services;

import java.util.LinkedList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.dp.primat.domain.Cathedra;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.LecturerType;
import ua.dp.primat.domain.Room;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.DayOfWeek;
import static org.junit.Assert.*;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.LessonDescription;
import ua.dp.primat.domain.lesson.LessonType;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.schedule.view.crosstab.LessonQueryItem;

/**
 *
 * @author work
 */
public class CrossTabServiceTest {

    public CrossTabServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetCrossTabItems() {
        System.out.println("CrossTabService");
        List<Lesson> listLesson = new LinkedList<Lesson>();
        for (DayOfWeek day : DayOfWeek.values()) {
            for (int i = 1; i < 5; i++) {
                Discipline d = new Discipline("Mat an" + i + day, new Cathedra("MMMT"));
                LessonDescription description = new LessonDescription(d,
                        new StudentGroup("PZ", 1L, 2008L),
                        1L, LessonType.LECTURE,
                        new Lecturer("wer lkj lkjkl", new Cathedra("234"), LecturerType.DOCENT),
                        null);
                Lesson l = new Lesson(Long.valueOf(i), WeekType.BOTH, day, new Room(3L, 32L), description);
                listLesson.add(l);
            }
        }

        CrossTabService instance = new CrossTabService();
        List<LessonQueryItem> result = instance.getCrossTabItems(listLesson);

        for (int i = 1; i < 5; i++) {
            LessonQueryItem row = result.get(i*2-2);
            for (DayOfWeek day : DayOfWeek.values()) {
                Lesson lessonForDay = row.getLessonForDay(day);
                //System.out.println((lessonForDay != null)?lessonForDay.getLessonDescription().getDiscipline().getName():"");
                assertEquals("asas", listLesson.get(i+4*day.getNumber()-1), lessonForDay);
            }
        }
       
    }
}
