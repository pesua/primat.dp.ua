/*
 *  
 */

package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="WorkloadEntry")
@NamedQueries({
    @NamedQuery(name="getWorkloadEntries", query="select we from WorkloadEntry we join we.workloadEntryPK.workloadId w join w.groups g where we.workloadEntryPK.semesterNumber = :semester and g = :group")
})
public class WorkloadEntry implements Serializable {
    @Id
    private WorkloadEntryPK workloadEntryPK = new WorkloadEntryPK();

    @Column(name="lection_count")
    private Long lectionCount;

    @Column(name="practice_count")
    private Long practiceCount;

    @Column(name="lab_count")
    private Long labCount;

    @Column(name="ind_count")
    private Long indCount;

    @Column(name="final_control")
    private FinalControlType finalControl;

    @Column(name="cource_work")
    private Boolean courceWork;

    @OneToMany(mappedBy="workloadEntry", cascade = CascadeType.ALL)
    private List<IndividualControl> individualControl = new ArrayList<IndividualControl>();

    public WorkloadEntry() {
    }

    public Long getSemesterNumber() {
        return workloadEntryPK.getSemesterNumber();
    }

    public void setSemesterNumber(Long semesterNumber) {
        workloadEntryPK.setSemesterNumber(semesterNumber);
    }

    public Workload getWorkload() {
        return workloadEntryPK.getWorkloadId();
    }

    public void setWorkload(Workload workload) {
        workloadEntryPK.setWorkloadId(workload);
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
}
