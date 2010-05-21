package ua.dp.primat.curriculum.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;


public class DataUtils {

    public DataUtils() {
    }

    @PersistenceContext(unitName = "curriculum")
    private EntityManager em;

    @Transactional
    public List<StudentGroup> getGroups(){
        @SuppressWarnings("unchecked")
        final List<StudentGroup> groups = em.createQuery("from StudentGroup").getResultList();

        return groups;
    }

    @Transactional
    public List<WorkloadEntry> getWorkloadEntries(StudentGroup group, Long semester){
        @SuppressWarnings("unchecked")
        List<WorkloadEntry> entries = em.createQuery("from WorkloadEntry where semesterNumber = " + semester).getResultList();

        return entries;
    }

    public long getSemesterCount(StudentGroup group) {
        return 8;
    }

    public boolean isCurriculumsExist()
    {
        return !getGroups().isEmpty();
    }
}
