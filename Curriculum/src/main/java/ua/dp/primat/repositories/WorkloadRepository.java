package ua.dp.primat.repositories;

import ua.dp.primat.domain.workload.Workload;

public interface WorkloadRepository {
    void store(Workload workload);
}
