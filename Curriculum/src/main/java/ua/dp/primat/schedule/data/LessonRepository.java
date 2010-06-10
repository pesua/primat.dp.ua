/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.data;

import java.util.List;
import ua.dp.primat.curriculum.data.StudentGroup;

/**
 *
 * @author pesua
 */
public interface LessonRepository {
    void remove(Lesson lesson);
    List<Lesson> getLessons(StudentGroup group);
    void store(Lesson lesson);

}
