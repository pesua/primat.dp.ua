package ua.dp.primat.curriculum.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

public class StudentGroupRepositoryImpl implements StudentGroupRepository {
    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;
    
    @Transactional
    public List<StudentGroup> getGroups() {
        Query query = ((Session)em.getDelegate()).getNamedQuery("getGroups");
        @SuppressWarnings("unchecked")
        List<StudentGroup> groups = query.list();
        return groups;
    }

    @Transactional
    public long getSemesterCount(StudentGroup group){
        em.createQuery("select MAX(n.workloadEntryPK.semesterNumber) from WorkloadEntry n INNER JOIN n.workloadEntryPK.workloadId.groups sgroup with sgroup.groupId=1");
        return 8;
    }

    @Transactional
    public void store(StudentGroup studentGroup) {
        if (em.contains(studentGroup)) {
            em.merge(studentGroup);
        }
        else {
            em.persist(studentGroup);
        }
    }
}
