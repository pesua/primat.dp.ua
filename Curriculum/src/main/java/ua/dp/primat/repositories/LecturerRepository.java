/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.repositories;

import ua.dp.primat.domain.Lecturer;
import java.util.List;
import ua.dp.primat.domain.Cathedra;

/**
 *
 * @author Administrator
 */
public interface LecturerRepository {

    void delete(Lecturer lecturer);

    List<Lecturer> getAllLecturers();

    List<Lecturer> getLecturerByCathedra(Cathedra cathedra);
    Lecturer getLecturerByName(String name);

    void store(Lecturer lecturer);

}
