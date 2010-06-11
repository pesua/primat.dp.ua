package ua.dp.primat.services;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.workload.Workload;
import ua.dp.primat.repositories.StudentGroupRepository;
import ua.dp.primat.repositories.WorkloadRepository;

/**
 * 
 * @author EniSh
 */
@Service
@Transactional
public class WorkloadService {
    public void storeWorkloads(List<Workload> workloads) {
        if (!workloads.isEmpty()) {
            groupRepository.store(workloads.get(0).getStudentGroup());
        }

        for(Workload w : workloads){
            workloadRepository.store(w);
        }
    }

    @Resource
    private WorkloadRepository workloadRepository;

    @Resource
    private StudentGroupRepository groupRepository;
}
