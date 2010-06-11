package ua.dp.primat.repositories;

import java.util.List;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.workload.Workload;

public interface WorkloadRepository {
    void store(Workload workload);
    List<Workload> getWorkloadsByGroupAndSemester(StudentGroup group, Long semester);
}
