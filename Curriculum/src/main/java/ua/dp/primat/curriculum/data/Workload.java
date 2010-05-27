package ua.dp.primat.curriculum.data;

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
import javax.persistence.Table;

@Entity
@Table(name = "workloads")
public class Workload implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    private Long workloadId;

    @ManyToOne
    @JoinColumn(name="discipline_fk")
    private Discipline discipline;

    @Column(name="type")
    private WorkloadType type;

    @Column(name="load_category")
    private LoadCategory loadCategory;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "workloads_groups",
        joinColumns =
        @JoinColumn(name = "workloadId"),
        inverseJoinColumns =
        @JoinColumn(name = "groupId"))
    private List<StudentGroup> groups = new ArrayList<StudentGroup>();

    @OneToMany(mappedBy="workloadEntryPK.workloadId", cascade = CascadeType.ALL)
    @Column(name="entries")
    private List<WorkloadEntry> entries = new ArrayList<WorkloadEntry>();

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
