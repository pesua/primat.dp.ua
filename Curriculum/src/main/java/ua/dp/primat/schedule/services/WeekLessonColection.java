package ua.dp.primat.schedule.services;

import java.util.ArrayList;
import java.util.List;
import ua.dp.primat.domain.lesson.Lesson;

/**
 *
 * @author EniSh
 */
public class WeekLessonColection {

    public WeekLessonColection() {
        lessonItems = new LessonItem[7][5];
        for (int i = 0; i < lessonItems.length; i++) {
            for (int j = 0; j < lessonItems[i].length; j++){
                lessonItems[i][j] = new LessonItem();
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

    public LessonItem[][] getLessonItems() {
        return lessonItems;
    }

    private LessonItem[][] lessonItems;
}
