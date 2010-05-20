package ua.dp.primat.curriculum.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DataUtils {

    private DataUtils() {
    }
    
    public static List<StudentGroup> getGroups(){
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("curriculum");
        EntityManager em = emFactory.createEntityManager();

        em.getTransaction().begin();

        @SuppressWarnings("unchecked")
        final List<StudentGroup> groups = em.createQuery("from StudentGroup").getResultList();

        em.close();
        return groups;
    }

    public static List<WorkloadEntry> getWorkloadEntries(StudentGroup group, Long semester){
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("curriculum");
        EntityManager em = emFactory.createEntityManager();

        em.getTransaction().begin();

        @SuppressWarnings("unchecked")
        List<WorkloadEntry> entries = em.createQuery("from WorkloadEntry where semesterNumber = " + semester).getResultList();

        em.close();
        return entries;
    }

    public static long getSemesterCount(StudentGroup group) {
        return 8;
    }

    public static boolean isCurriculumsExist()
    {
        return !getGroups().isEmpty();
    }
}
