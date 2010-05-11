/*
 *  
 */

package ua.dp.primat;

/**
 *
 * @author EniSh
 */
public class Discipline {
    Long disciplineId;
    String name;
    Long cathedraId;

    public Long getCathedraId() {
        return cathedraId;
    }

    public void setCathedraId(Long cathedraId) {
        this.cathedraId = cathedraId;
    }

    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
