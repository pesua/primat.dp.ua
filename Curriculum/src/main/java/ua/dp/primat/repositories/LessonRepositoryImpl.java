package ua.dp.primat.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.Lesson;

/**
 *
 * @author pesua
 */
@Repository
public class LessonRepositoryimpl implements LessonRepository {
    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

    public void store(Lesson lesson) {
        if (em.contains(lesson)) {
            em.merge(lesson);
        }
        else {
            em.persist(lesson);
        }
    }

    public void remove(Lesson lesson) {
        if (em.contains(lesson)) {
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

}
