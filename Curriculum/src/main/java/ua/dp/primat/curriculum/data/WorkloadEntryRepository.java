package ua.dp.primat.curriculum.data;

import java.util.List;

public interface WorkloadEntryRepository {
    List<WorkloadEntry> getWorkloadEntries(StudentGroup group, Long semester);
}
