package ua.dp.primat.curriculum.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
