package ua.dp.primat.schedule.data;

import java.util.List;
import ua.dp.primat.curriculum.data.StudentGroup;

/**
 *
 * @author pesua
 */
public interface LessonRepository {
    void store(Lesson lesson);
    void remove(Lesson lesson);
    List<Lesson> getLessons(StudentGroup group);
    List<Lesson> getLessonsByGroupAndSemester(StudentGroup group, Long semester);
}
