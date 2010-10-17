package ua.dp.primat.repositories;

import java.util.Collections;
import ua.dp.primat.domain.Lecturer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.Cathedra;

/**
 *
 * @author EniSh
 */
@Repository("lecturerRepository")
@Transactional
public class LecturerRepositoryImpl implements LecturerRepository {

    public void store(Lecturer lecturer) {
        if (em.contains(lecturer) || (lecturer.getId() != null)) {
            em.merge(lecturer);
        } else {
            em.persist(lecturer);
        }
    }

    public List<Lecturer> getAllLecturers() {
        final List<Lecturer> lst = em.createNamedQuery(Lecturer.GET_ALL_LECTURERS_QUERY).getResultList();
        Collections.sort(lst);
        return lst;
    }

    public List<Lecturer> getLecturerByCathedra(Cathedra cathedra) {
        final List<Lecturer> lst = em.createNamedQuery(Lecturer.GET_LECTURERS_BY_CATHEDRA_QUERY).setParameter("Cathedra", cathedra).getResultList();
        Collections.sort(lst);
        return lst;
    }

    public Lecturer getLecturerByName(String name) {
        try {
            return (Lecturer) em.createNamedQuery(Lecturer.GET_LECRURER_BY_NAME_QUERY).setParameter("name", name).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public void delete(Lecturer lecturer) {
        final Lecturer l = em.find(Lecturer.class, lecturer.getId());
        if (em.contains(l)) {
            em.remove(l);
        }
    }
    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;
    
}
