/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.data;

import java.util.List;
import ua.dp.primat.curriculum.data.Cathedra;

/**
 *
 * @author Administrator
 */
public interface LecturerRepository {

    void delete(Lecturer lecturer);

    List<Lecturer> getAllLecturers();

    List<Lecturer> getLecturerByCathedra(Cathedra cathedra);

    void store(Lecturer lecturer);

}
