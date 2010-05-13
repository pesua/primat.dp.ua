/*
 *  
 */

package ua.dp.primat.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author EniSh
 */
@Entity
@Table(name="WorkloadsEntry")
public class WorkloadEntry implements Serializable {
    //
    @Id
    WorkloadEntryPK workloadEntryId;
    
    //Long workloadId;
    //Long sessionNumber;

    @Column(name="semester_number")
    Long semesterNumber;

    @Column(name="lection_count")
    Long lectionCount;

    @Column(name="practice_count")
    Long practiceCount;

    @Column(name="lab_count")
    Long labCount;

    @Column(name="ind_count")
    Long indCount;

    @Column(name="final_control")
    FinalControlType finalControl;

    @Column(name="cource_work")
    Boolean courceWork;

    @OneToMany
    @Column(name="individual_control")
    List<IndividualControl> individualControl;

    public WorkloadEntry() {
    }
}
