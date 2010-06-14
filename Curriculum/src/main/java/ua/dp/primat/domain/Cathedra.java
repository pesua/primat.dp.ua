package ua.dp.primat.domain;

import ua.dp.primat.domain.workload.Discipline;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cathedra implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    @OneToMany(mappedBy = "cathedra")
    private Set<Discipline> disciplines;

    public Cathedra() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long cathedraId) {
        this.id = cathedraId;
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
