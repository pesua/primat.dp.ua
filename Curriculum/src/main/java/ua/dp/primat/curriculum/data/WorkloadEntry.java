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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="WorkloadEntry")
@NamedQueries({
    @NamedQuery(name="getWorkloadEntries", query="select we from WorkloadEntry we join we.parentWorkload w join w.groups g where we.semesterNumber = :semester and g = :group")
})
public class WorkloadEntry implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long workloadEntryPk;
    //private WorkloadEntryPK workloadEntryPK = new WorkloadEntryPK();

    private Long semesterNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    private Workload parentWorkload;

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

    @OneToMany(mappedBy="workloadEntry")//, cascade = CascadeType.ALL
    private List<IndividualControl> individualControl = new ArrayList<IndividualControl>();

    public WorkloadEntry() {
    }

    public Workload getParentWorkload() {
        return parentWorkload;
    }

    public void setParentWorkload(Workload parentWorkload) {
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

    public Workload getWorkload() {
        return parentWorkload;
    }

    public void setWorkload(Workload workload) {
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
}
