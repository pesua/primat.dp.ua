/*
 *  
 */

package ua.dp.primat;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author EniSh
 */
public class Workload {
    // entity key
    Long workloadId;
    // descipline which this one related to
    Long disceplineId;
    // number of semester when workload was valid
    Long semesterNumber;
    // 
    Long type;
    Long lectionCount;
    Long practiceCount;
    Long labCount;
    Long finalControl;
    Boolean courceWork;
    Set groups = new HashSet();
    Set personalControl = new HashSet();

    public Boolean getCourceWork() {
        return courceWork;
    }

    public void setCourceWork(Boolean courceWork) {
        this.courceWork = courceWork;
    }

    public Long getDisceplineId() {
        return disceplineId;
    }

    public void setDisceplineId(Long disceplineId) {
        this.disceplineId = disceplineId;
    }

    public Long getFinalControl() {
        return finalControl;
    }

    public void setFinalControl(Long finalControl) {
        this.finalControl = finalControl;
    }

    public Set getGroups() {
        return groups;
    }

    public void setGroups(Set groups) {
        this.groups = groups;
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

    public Set getPersonalControl() {
        return personalControl;
    }

    public void setPersonalControl(Set personalControl) {
        this.personalControl = personalControl;
    }

    public Long getPracticeCount() {
        return practiceCount;
    }

    public void setPracticeCount(Long practiceCount) {
        this.practiceCount = practiceCount;
    }

    public Long getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(Long semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(Long workloadId) {
        this.workloadId = workloadId;
    }
}
