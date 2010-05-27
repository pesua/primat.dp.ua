package ua.dp.primat.curriculum.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

public class WorkloadRepositoryImpl implements WorkloadRepository {
    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

    @Transactional
    public void store(Workload workload) {
        if (em.contains(workload)) {
            
            em.merge(workload);
        }
        else {
            em.persist(workload);
        }
    }
    
}
