/*
 *  
 */

package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author EniSh
 */
@Entity
@Table(name="discipline")
public class Discipline implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long disciplineId;

    @Column(name="name")
    String name;
    
    @ManyToOne
    Cathedra cathedra;
    
    @OneToMany(mappedBy = "discipline")
    private List<Workload> workloads;

    public Discipline() {
    }

    public Cathedra getCathedra() {
        return cathedra;
    }

    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
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

    public List<Workload> getWorkloads() {
        return workloads;
    }

    public void setWorkloads(List<Workload> workloads) {
        this.workloads = workloads;
    }
}
