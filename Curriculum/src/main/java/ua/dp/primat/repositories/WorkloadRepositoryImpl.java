package ua.dp.primat.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.workload.Workload;

@Repository
public class WorkloadRepositoryImpl implements WorkloadRepository {
    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

    public void store(Workload workload) {
        em.persist(workload);
    }

    @Transactional
    public List<Workload> getWorkloadsByGroupAndSemester(StudentGroup group, Long semester) {
        Query q = em.createNamedQuery(Workload.GET_WORKLOAD_BY_GROUP_AND_SEMESTER);
        q.setParameter("group", group);
        q.setParameter("semester", semester);
        return q.getResultList();
    }
    
}
