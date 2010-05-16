/*
 *  
 */

package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author EniSh
 */
@Entity
@Table(name="WorkloadEntry")
public class WorkloadEntry implements Serializable {
    //
    @Id
    WorkloadEntryPK workloadEntryPK = new WorkloadEntryPK();

    @Column(name="lection_count")
    Long lectionCount;

    @Column(name="practice_count")
    Long practiceCount;

    @Column(name="lab_count")
    Long labCount;

    // individual work lessons count
    @Column(name="ind_count")
    Long indCount;

    @Column(name="final_control")
    FinalControlType finalControl;

    @Column(name="cource_work")
    Boolean courceWork;

    @OneToMany(mappedBy="workloadEntry")
    List<IndividualControl> individualControl = new Vector<IndividualControl>();

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
