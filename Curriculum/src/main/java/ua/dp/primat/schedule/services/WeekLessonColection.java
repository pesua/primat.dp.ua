package ua.dp.primat.schedule.services;

import java.util.ArrayList;
import java.util.List;
import ua.dp.primat.schedule.data.Lesson;

/**
 *
 * @author EniSh
 */
public class WeekLessonColection {

    public WeekLessonColection() {
        lessonItems = new LessonItem[7][5];
        for (LessonItem[] lis : lessonItems) {
            for (LessonItem li : lis) {
                li = new LessonItem();
            }
        }
    }

    public WeekLessonColection(List<Lesson> lessons) {
        this();
        for (Lesson lesson : lessons) {
            lessonItems[lesson.getDayOfWeek().getNumber()][lesson.getLessonNumber().intValue()].mergeWithLesson(lesson);
        }
    }
    
    public List<LessonItem[]> getDayList() {
        List<LessonItem[]> list = new ArrayList<LessonItem[]>();
        for (LessonItem[] li : lessonItems) {
            list.add(li);
        }
        return list;
    }

    private LessonItem[][] lessonItems;
}
