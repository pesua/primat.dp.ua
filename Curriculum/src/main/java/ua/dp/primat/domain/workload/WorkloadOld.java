package ua.dp.primat.domain.workload;

import java.util.ArrayList;
import java.util.List;
import ua.dp.primat.domain.StudentGroup;

public class WorkloadOld {
    private Long workloadId;

    private Discipline discipline;

    private WorkloadType type;

    private LoadCategory loadCategory;

    private List<StudentGroup> groups = new ArrayList<StudentGroup>();

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
