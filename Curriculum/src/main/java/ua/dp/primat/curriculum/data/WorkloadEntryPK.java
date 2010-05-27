package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class WorkloadEntryPK implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private Workload workloadId;

    private Long semesterNumber;

    public Long getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(Long semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public Workload getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(Workload workloadId) {
        this.workloadId = workloadId;
    }

    public WorkloadEntryPK() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WorkloadEntryPK other = (WorkloadEntryPK) obj;
        if (this.workloadId != other.workloadId && (this.workloadId == null || !this.workloadId.equals(other.workloadId))) {
            return false;
        }
        if (this.semesterNumber != other.semesterNumber && (this.semesterNumber == null || !this.semesterNumber.equals(other.semesterNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int START_HASH_VALUE = 5;
        int hash = START_HASH_VALUE;
        final int root = 97;
        hash = root * hash + (this.workloadId != null ? this.workloadId.hashCode() : 0);
        hash = root * hash + (this.semesterNumber != null ? this.semesterNumber.hashCode() : 0);
        return hash;
    }
}
