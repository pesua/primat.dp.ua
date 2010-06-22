package ua.dp.primat.repositories;

import ua.dp.primat.domain.lesson.LessonDescription;

/**
 *
 * @author fdevelop
 */
public interface LessonDescriptionRepository {
    void store(LessonDescription lessonDescription);
    void remove(LessonDescription lessonDescription);
}
