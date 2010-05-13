/*
 *  
 */

package ua.dp.primat.data;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author EniSh
 */
@Embeddable
public class WorkloadEntryPK implements Serializable {
    Long workloadId;
    Long sessionNumber;
}
