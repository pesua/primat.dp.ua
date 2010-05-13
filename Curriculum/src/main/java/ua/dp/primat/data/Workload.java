/*
 *  Учебная нагрузка для определенных груп на определенную дисциплину
 */

package ua.dp.primat.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @Column(name="id")
    Long workloadId;

    // descipline which this one related to
    @ManyToOne
    @Column(name="discipline")
    Discipline discipline;

    // Учебная нагрузка может быть привязана к нескольким группам
    @ManyToMany
    @Column(name="groups")
    List<StudentGroup> groups;

    // Учебная нагрузка для группы может быть профильная, непрофильная и т. д.
    @Column(name="type")
    WorkloadType type;

    @Column(name="load_category")
    LoadCategory loadCategory;

    // У нее есть определенные записи на каждый семестр
    @OneToMany
    @Column(name="entries")
    List<WorkloadEntry> entries;

    public Long getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(Long workloadId) {
        this.workloadId = workloadId;
    }

    public Workload() {
    }
}
