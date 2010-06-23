package ua.dp.primat.schedule.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ua.dp.primat.domain.lesson.Lesson;

/**
 *
 * @author EniSh
 */
public class WeekLessonColection implements Serializable {

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
            int dayNumber = lesson.getDayOfWeek().getNumber();
            int lessonNumber = lesson.getLessonNumber().intValue();
            lessonItems[dayNumber][lessonNumber].mergeWithLesson(lesson);
        }
    }
    
    public List<LessonItem[]> getDayList() {
        List<LessonItem[]> list = new ArrayList<LessonItem[]>();
        list.addAll(Arrays.asList(lessonItems));
        return list;
    }

    public LessonItem[][] getLessonItems() {
        return lessonItems;
    }

    private LessonItem[][] lessonItems;
}
