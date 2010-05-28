package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Cathedra")
public class Cathedra implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "cathedra_id")
    private Long cathedraId;

    @SuppressWarnings("MagicNumber")
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy = "cathedra")//, cascade = CascadeType.ALL
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
