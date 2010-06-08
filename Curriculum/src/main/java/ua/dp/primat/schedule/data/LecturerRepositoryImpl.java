package ua.dp.primat.schedule.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ua.dp.primat.curriculum.data.Cathedra;

/**
 *
 * @author Administrator
 */
public class LecturerRepositoryImpl {
    public void store(Lecturer lecturer){
        if (em.contains(lecturer)) {
            em.merge(lecturer);
        } else {
            em.persist(lecturer);
        }
    }

    public List<Lecturer> getAllLecturers() {
        return em.createNamedQuery(Lecturer.GET_ALL_LECTURERS).getResultList();
    }

    public List<Lecturer> getLecturerByCathedra(Cathedra cathedra) {
        return em.createNamedQuery(Lecturer.GET_LECTURERS_BY_CATHEDRA).setParameter("Cathedra", cathedra).getResultList();
    }

    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;
}
