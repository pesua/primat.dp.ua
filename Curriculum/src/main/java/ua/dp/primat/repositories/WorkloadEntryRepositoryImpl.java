package ua.dp.primat.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.workload.WorkloadEntry;

@Repository
public class WorkloadEntryRepositoryImpl implements WorkloadEntryRepository {
    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

    @Transactional
    public List<WorkloadEntry> getWorkloadEntries(StudentGroup group, Long semester) {
        Query query = ((Session) em.getDelegate()).getNamedQuery("getWorkloadEntries");
        query.setParameter("semester", semester);
        query.setParameter("group", group);
        
        return query.list();
    }
}