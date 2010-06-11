/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
        Query query = ((Session)em.getDelegate()).getNamedQuery(Lesson.GET_LESSONS_BY_GROUP_QUERY);
        query.setParameter("group", group);
        return query.list();
    }

    public List<Lesson> getLessonsByGroupAndSemester(StudentGroup group, Long semester) {
        Query query = ((Session)em.getDelegate()).getNamedQuery(Lesson.GET_LESSONS_BY_GROUP_AND_SEMESTER_QUERY);
        query.setParameter("group", group);
        query.setParameter("semester", semester);
        return query.list();
    }

}
