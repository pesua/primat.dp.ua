package ua.dp.primat.repositories;

import java.util.List;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.Room;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.schedule.services.Semester;

/**
 *
 * @author pesua
 */
public interface LessonRepository {
    void store(Lesson lesson);
    void remove(Lesson lesson);
    List<Lesson> getLessonsByGroupAndDay(StudentGroup group, Long semester, DayOfWeek dayOfWeek, WeekType weekType);
    List<Lesson> getLessonsByGroupAndSemester(StudentGroup group, Long semester);
    List<Lesson> getLessonsByLecturerAndSemester(Lecturer lecturer, Semester semester);
    List<Lesson> getLessonsByRoomAndSemester(Room room, Semester semester);
    Lesson find(Long id);
}
