/*
 *  
 */

package ua.dp.primat.domain.workload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name="getWorkloadEntries", query="select we from WorkloadEntry we join we.parentWorkload w join w.groups g where we.semesterNumber = :semester and g = :group")
})
public class WorkloadEntry implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long workloadEntryPk;

    private Long semesterNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    private WorkloadOld parentWorkload;

    private Long lectionCount;

    private Long practiceCount;

    private Long labCount;

    private Long indCount;

    private FinalControlType finalControl;

    private Boolean courceWork;

    //TODO mappedBy="workloadEntry" o_O, WTF?
    @OneToMany(mappedBy="workloadEntry")//, cascade = CascadeType.ALL
    private List<IndividualControl> individualControl = new ArrayList<IndividualControl>();

    public WorkloadEntry() {
    }

    public WorkloadOld getParentWorkload() {
        return parentWorkload;
    }

    public void setParentWorkload(WorkloadOld parentWorkload) {
        this.parentWorkload = parentWorkload;
    }

    public Long getWorkloadEntryPk() {
        return workloadEntryPk;
    }

    public void setWorkloadEntryPk(Long workloadEntryPk) {
        this.workloadEntryPk = workloadEntryPk;
    }

    public Long getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(Long semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public WorkloadOld getWorkload() {
        return parentWorkload;
    }

    public void setWorkload(WorkloadOld workload) {
        parentWorkload = workload;
    }

    public Boolean getCourceWork() {
        return courceWork;
    }

    public void setCourceWork(Boolean courceWork) {
        this.courceWork = courceWork;
    }

    public FinalControlType getFinalControl() {
        return finalControl;
    }

    public void setFinalControl(FinalControlType finalControl) {
        this.finalControl = finalControl;
    }

    public Long getIndCount() {
        return indCount;
    }

    public void setIndCount(Long indCount) {
        this.indCount = indCount;
    }

    public List<IndividualControl> getIndividualControl() {
        return individualControl;
    }

    public void setIndividualControl(List<IndividualControl> individualControl) {
        this.individualControl = individualControl;
    }

    public Long getLabCount() {
        return labCount;
    }

    public void setLabCount(Long labCount) {
        this.labCount = labCount;
    }

    public Long getLectionCount() {
        return lectionCount;
    }

    public void setLectionCount(Long lectionCount) {
        this.lectionCount = lectionCount;
    }

    public Long getPracticeCount() {
        return practiceCount;
    }

    public void setPracticeCount(Long practiceCount) {
        this.practiceCount = practiceCount;
    }

    public Workload toNew(){
        Workload w = new Workload();
        w.setCourseWork(courceWork);
        w.setDiscipline(parentWorkload.getDiscipline());
        w.setFinalControlType(finalControl);
        w.setIndividualControl(individualControl);
        w.setLaboratoryHours(labCount);
        w.setLectionHours(lectionCount);
        w.setLoadCategory(parentWorkload.getLoadCategory());
        w.setPracticeHours(practiceCount);
        w.setSelfworkHours(indCount);
        w.setSemesterNumber(semesterNumber);
        w.setStudentGroup(parentWorkload.getGroups().get(0));
        w.setType(parentWorkload.getType());

        return w;
    }
}
