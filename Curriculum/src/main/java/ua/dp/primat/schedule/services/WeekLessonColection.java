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
    private static final long serialVersionUID = 1L;

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
            final int dayNumber = lesson.getDayOfWeek().getNumber();
            final int lessonNumber = lesson.getLessonNumber().intValue();
            lessonItems[dayNumber][lessonNumber - 1].mergeWithLesson(lesson);
        }
    }
    
    public List<LessonItem[]> getDayList() {
        final List<LessonItem[]> list = new ArrayList<LessonItem[]>();
        list.addAll(Arrays.asList(lessonItems));
        return list;
    }

    public LessonItem[][] getLessonItems() {
        return lessonItems;
    }

    private LessonItem[][] lessonItems;
}
