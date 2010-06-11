package ua.dp.primat.domain.workload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.IndexColumn;
import ua.dp.primat.domain.StudentGroup;

@Entity
public class WorkloadOld implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long workloadId;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="discipline_fk")
    private Discipline discipline;

    private WorkloadType type;

    private LoadCategory loadCategory;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "workloads_groups",
        joinColumns =
        @JoinColumn(name = "workloadId"),
        inverseJoinColumns =
        @JoinColumn(name = "groupId"))
    private List<StudentGroup> groups = new ArrayList<StudentGroup>();

    @IndexColumn(name="parentWorkloadIndex")
    @OneToMany(cascade = CascadeType.ALL, mappedBy="parentWorkload")
    private List<WorkloadEntry> entries = new ArrayList<WorkloadEntry>();

    public WorkloadOld() {
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
