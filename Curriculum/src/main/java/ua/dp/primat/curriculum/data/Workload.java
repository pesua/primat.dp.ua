/*
 *  Учебная нагрузка для определенных груп на определенную дисциплину
 */

package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author EniSh
 */
@Entity
@Table(name="workloads")
public class Workload implements Serializable {
    // entity key
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    Long workloadId;

    // descipline which this one related to
    @ManyToOne
    @JoinColumn(name="discipline_fk")
    //@Column(name="discipline")
    Discipline discipline;

    // Учебная нагрузка для группы может быть профильная, непрофильная и т. д.
    @Column(name="type")
    WorkloadType type;

    @Column(name="load_category")
    LoadCategory loadCategory;

    // Учебная нагрузка может быть привязана к нескольким группам
    @ManyToMany
    List<StudentGroup> groups = new Vector<StudentGroup>();

    // У нее есть определенные записи на каждый семестр
    @OneToMany(mappedBy="workloadId")
    @Column(name="entries")
    List<WorkloadEntry> entries;

    public Workload() {
    }

    public Long getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(Long workloadId) {
        this.workloadId = workloadId;
    }

    public List<StudentGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<StudentGroup> groups) {
        this.groups = groups;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public List<WorkloadEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<WorkloadEntry> entries) {
        this.entries = entries;
    }

    public LoadCategory getLoadCategory() {
        return loadCategory;
    }

    public void setLoadCategory(LoadCategory loadCategory) {
        this.loadCategory = loadCategory;
    }

    public WorkloadType getType() {
        return type;
    }

    public void setType(WorkloadType type) {
        this.type = type;
    }
}
