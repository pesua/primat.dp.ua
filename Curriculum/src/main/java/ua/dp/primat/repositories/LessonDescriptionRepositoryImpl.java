package ua.dp.primat.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ua.dp.primat.domain.lesson.LessonDescription;

/**
 *
 * @author fdevelop
 */
@Repository("lessonDescriptionRepository")
public class LessonDescriptionRepositoryImpl implements LessonDescriptionRepository {

    public void store(LessonDescription lessonDescription) {
        final LessonDescription merged = em.merge(lessonDescription);
        em.flush();
        lessonDescription.setId(merged.getId());
    }

    public void remove(LessonDescription lessonDescription) {
        final LessonDescription l = em.find(LessonDescription.class, lessonDescription.getId());
        if (l != null) {
            em.remove(lessonDescription);
        }
    }

    public LessonDescription find(Long id) {
        return em.find(LessonDescription.class, id);
    }

    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;
}
