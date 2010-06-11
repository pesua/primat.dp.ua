package ua.dp.primat.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.StudentGroup;

@Repository("studentGroupRepositoty")
public class StudentGroupRepositoryImpl implements StudentGroupRepository {
    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

    @Transactional
    public List<StudentGroup> getGroups() {
        Query query = ((Session)em.getDelegate()).getNamedQuery("getGroups");
        return query.list();
    }

    public void store(StudentGroup studentGroup) {
        if (em.contains(studentGroup)) {
            em.merge(studentGroup);
        }
        else {
            em.persist(studentGroup);
        }
    }

    @Transactional
    public void remove(StudentGroup studentGroup) {
        if (em.contains(studentGroup)) {
            em.remove(studentGroup);
        }
    }
}
