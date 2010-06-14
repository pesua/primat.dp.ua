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
    public WeekLessonColection getSchedule(StudentGroup group, Long semester) {
        List<Lesson> lessons = lessonRepository.getLessonsByGroupAndSemester(group, semester);
        return new WeekLessonColection(lessons);
    }

    public void setSchedule(StudentGroup group, Long semester, WeekLessonColection lessonColection) {
        LessonItem[][] lessonItems = lessonColection.getLessonItems();
        for (int i = 0; i < lessonItems.length; i++) {
            for (int j = 0; j < lessonItems[i].length; j++) {
                LessonItem lessonItem = lessonItems[i][j];
                lessonItem.getNumerator().toLesson(DayOfWeek.MONDAY, Long.valueOf(j));
            }
        }
    }

    @Resource
    private LessonRepository lessonRepository;
}
