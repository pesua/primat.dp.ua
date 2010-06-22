package ua.dp.primat.schedule.services;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.repositories.LessonRepository;

/**
 * Service which helps get and edit schedule.
 * @author EniSh
 */
@Service
@Transactional
public class EditScheduleService {
    /**
     * gets special collecton of lessons which help edit schedule
     * @param group
     * @param semester semester number of editable schedule
     * @return collection of lessons
     */
    public WeekLessonColection getSchedule(StudentGroup group, Long semester) {
        List<Lesson> lessons = lessonRepository.getLessonsByGroupAndSemester(group, semester);
        return new WeekLessonColection(lessons);
    }

    /**
     *
     * @param group
     * @param semester
     * @param lessonColection
     */
    public void setSchedule(StudentGroup group, Long semester, WeekLessonColection lessonColection) {
        LessonItem[][] lessonItems = lessonColection.getLessonItems();
        for (DayOfWeek day : DayOfWeek.values()) {
            for (int j = 0; j < lessonItems[day.getNumber()].length; j++) {
                saveLessonItem(lessonItems[day.getNumber()][j], day, j);
            }
        }
    }

    private void saveLessonItem(LessonItem lessonItem, DayOfWeek day, int lessonNumber) {
        saveEditableLesson(lessonItem.getNumerator(), day, lessonNumber);
        if (!lessonItem.isOneLesson()) {
            saveEditableLesson(lessonItem.getDenominator(), day, lessonNumber);
        }
    }

    private void saveEditableLesson(EditableLesson numerator, DayOfWeek day, int lessonNumber) {
        if (!numerator.isEmpty()) {
            Lesson lesson = numerator.toLesson(day, Long.valueOf(lessonNumber));
            lessonService.saveLesson(lesson);
        }
    }

    @Resource
    private LessonService lessonService;

    @Resource
    private LessonRepository lessonRepository;
}
