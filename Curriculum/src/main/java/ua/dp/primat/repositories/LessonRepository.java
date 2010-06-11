package ua.dp.primat.repositories;

import java.util.List;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.Lesson;

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
