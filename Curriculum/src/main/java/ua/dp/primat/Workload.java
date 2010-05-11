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
    Long workloadId;
    Long disceplineId;
    Long semesterNumber;
    Long type;
    Long lectionCount;
    Long practiceCount;
    Long labCount;
    Long finalControl;
    Boolean courceWork;
    Set groups = new HashSet();
    Set personalControl = new HashSet();
}
