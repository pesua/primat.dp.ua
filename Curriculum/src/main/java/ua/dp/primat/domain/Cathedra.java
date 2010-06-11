package ua.dp.primat.domain;

import ua.dp.primat.domain.workload.Discipline;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cathedra implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long cathedraId;

    private String name;
    
    @OneToMany(mappedBy = "cathedra")
    private Set<Discipline> disciplines;

    public Cathedra() {
    }

    public Long getCathedraId() {
        return cathedraId;
    }

    public void setCathedraId(Long cathedraId) {
        this.cathedraId = cathedraId;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
