package ua.dp.primat.schedule.services;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.schedule.data.Lesson;
import ua.dp.primat.schedule.data.LessonRepository;

/**
 * Service which helps get and edit schedule
 * @author EniSh
 */
@Service
@Transactional
public class EditScheduleService {
    public WeekLessonColection getSchedule(StudentGroup group, Long semester) {
        List<Lesson> lessons = lessonRepository.getLessonsByGroupAndSemester(group, semester);
        return new WeekLessonColection(lessons);
    }

    public void setSchedule(StudentGroup group) {

    }

    @Resource
    private LessonRepository lessonRepository;
}
