/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.curriculum.data;

import java.util.List;

/**
 *
 * @author pesua
 */
public interface DisciplineRepository {

    void delete(Discipline discipline);

    @SuppressWarnings(value = "unchecked")
    List<Discipline> getDisciplines();

    void store(Discipline discipline);

}

