package ua.dp.primat.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.StudentGroup;

@Repository("studentGroupRepositoty")
public class StudentGroupRepositoryImpl implements StudentGroupRepository {

    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<StudentGroup> getGroups() {
        return em.createNamedQuery(StudentGroup.GET_GROUPS_QUERY).getResultList();
    }

    public void store(StudentGroup studentGroup) {
        if (em.contains(studentGroup)) {
            em.merge(studentGroup);
        } else {
            em.persist(studentGroup);
        }
    }

    @Transactional
    public void remove(StudentGroup studentGroup) {
        em.remove(studentGroup);
    }
}
