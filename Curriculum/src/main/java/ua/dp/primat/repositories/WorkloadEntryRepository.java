package ua.dp.primat.repositories;

import java.util.List;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.workload.WorkloadEntry;

public interface WorkloadEntryRepository {
    List<WorkloadEntry> getWorkloadEntries(StudentGroup group, Long semester);
}
