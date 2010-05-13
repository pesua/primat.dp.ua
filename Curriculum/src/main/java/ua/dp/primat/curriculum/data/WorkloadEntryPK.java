/*
 *  ласс составного ключа дл€ WorkloadEntry.
 * “ак как запись полюбому относитьс€ к определенной нагрузки и об€зательно
 * прив€зана к определенному семестру
 */

package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author EniSh
 */
@Embeddable
public class WorkloadEntryPK implements Serializable {
    @Column(name = "workload_id")
    Long workloadId;

    @Column(name = "semester_number")
    Long semesterNumber;

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
        int hash = 5;
        hash = 97 * hash + (this.workloadId != null ? this.workloadId.hashCode() : 0);
        hash = 97 * hash + (this.semesterNumber != null ? this.semesterNumber.hashCode() : 0);
        return hash;
    }
}
