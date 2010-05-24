/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.curriculum.data;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
public interface WorkloadEntryRepository {

    @Transactional
    List<WorkloadEntry> getWorkloadEntries(StudentGroup group, Long semester);

}
