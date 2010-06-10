/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.curriculum.data.StudentGroup;

/**
 *
 * @author pesua
 */
public class LessonRepositoryimpl implements LessonRepository {
    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

    @Transactional
    public void store(Lesson lesson) {
        if (em.contains(lesson)) {
            em.merge(lesson);
        }
        else {
            em.persist(lesson);
        }
    }

    @Transactional
    public void remove(Lesson lesson) {
        if (em.contains(lesson)) {
            em.remove(lesson);
        }
    }

    @Override
    public List<Lesson> getLessons(StudentGroup group) {
        Query query = ((Session)em.getDelegate()).getNamedQuery("getLessons");
        query.setParameter("group", group);
        return query.list();
    }

}
