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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == WorkloadEntryPK.class)
        {
            WorkloadEntryPK pk = (WorkloadEntryPK)obj;
            return (pk.semesterNumber == this.semesterNumber)
                    && (pk.workloadId == this.workloadId);
        }
        else
            return super.equals(obj);
    }
}
