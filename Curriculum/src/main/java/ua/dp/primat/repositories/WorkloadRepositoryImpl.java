package ua.dp.primat.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.workload.Workload;

@Repository
@Transactional
public class WorkloadRepositoryImpl implements WorkloadRepository {
    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

    public void store(Workload workload) {
        if (em.contains(workload)) {
            
            em.merge(workload);
        }
        else {
            em.persist(workload);
        }
    }
    
}
