package ua.dp.primat.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.WeekType;

/**
 *
 * @author pesua
 */

@Repository("lessonRepository")
public class LessonRepositoryImpl implements LessonRepository {

    public void store(Lesson lesson) {
        Lesson merged = em.merge(lesson);
        em.flush();
        lesson.setId(merged.getId());
    }

    public void remove(Lesson lesson) {
        Lesson l = em.find(Lesson.class, lesson.getId());
        if (l != null) {
            em.remove(lesson);
        }
    }

    public List<Lesson> getLessons(StudentGroup group) {
        Query query = em.createNamedQuery(Lesson.GET_LESSONS_BY_GROUP_QUERY);
        query.setParameter("group", group);
        return query.getResultList();
    }

    public List<Lesson> getLessonsByGroupAndSemester(StudentGroup group, Long semester) {
        Query query = em.createNamedQuery(Lesson.GET_LESSONS_BY_GROUP_AND_SEMESTER_QUERY);
        query.setParameter("group", group);
        query.setParameter("semester", semester);
        return query.getResultList();
    }

    public List<Lesson> getLessonsByGroupAndDay(StudentGroup group, Long semester, DayOfWeek dayOfWeek, WeekType weekType) {
        Query query = em.createNamedQuery(Lesson.GET_LESSONS_BY_GROUP_AND_SEMESTER_QUERY);
        query.setParameter("group", group);
        query.setParameter("semester", semester);
        query.setParameter("day", dayOfWeek);
        query.setParameter("weekType", dayOfWeek);
        return query.getResultList();
    }

    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

}
