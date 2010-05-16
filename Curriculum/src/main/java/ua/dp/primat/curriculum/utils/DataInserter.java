/*
 *  DataInserter
 */

package ua.dp.primat.curriculum.utils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import ua.dp.primat.curriculum.data.Cathedra;
import ua.dp.primat.curriculum.data.Discipline;
import ua.dp.primat.curriculum.data.Workload;
import ua.dp.primat.curriculum.data.WorkloadEntry;

/**
 *
 * @author fdevelop
 */
public class DataInserter {

    public static Cathedra addCathedra(EntityManager em, Cathedra c) {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();

        String q = String.format("from Cathedra where name = '%s'",c.getName());
        System.out.println(q);
        Cathedra ck;
        try
        {
            ck = (Cathedra)em.createQuery(q).getSingleResult();
        }
        catch (NoResultException nr) {
            ck = c;
            em.persist(ck);
        }
        catch (NonUniqueResultException nur) {
            ck = (Cathedra)em.createQuery(q).getResultList().get(0);
        }
        //em.getTransaction().commit();
        return ck;
    }

    public static Discipline addDiscipline(EntityManager em, Discipline c) {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();

        String q = String.format("from Discipline where name = '%s' and cathedra = '%d'",c.getName(),c.getCathedra().getCathedraId());
        System.out.println(q);
        Discipline ck;
        try
        {
            ck = (Discipline)em.createQuery(q).getSingleResult();
        }
        catch (NoResultException nr) {
            ck = c;
            em.persist(ck);
        }
        catch (NonUniqueResultException nur) {
            ck = (Discipline)em.createQuery(q).getResultList().get(0);
        }
        //em.getTransaction().commit();
        return ck;
    }

    public static Workload addWorkload(EntityManager em, Workload c) {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();

        String q = String.format("from Workload where discipline_fk = '%d' and load_category = '%d' and type = '%d'",c.getDiscipline().getDisciplineId(), c.getLoadCategory().ordinal(), c.getType().ordinal());
        System.out.println(q);
        Workload ck;
        try
        {
            ck = (Workload)em.createQuery(q).getSingleResult();
        }
        catch (NoResultException nr) {
            ck = c;
            em.persist(ck);
        }
        catch (NonUniqueResultException nur) {
            ck = (Workload)em.createQuery(q).getResultList().get(0);
        }
        //em.getTransaction().commit();
        return ck;
    }

    public static WorkloadEntry addWorkloadEntry(EntityManager em, WorkloadEntry c) {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();

        String q = String.format("from WorkloadEntry where workloadId_id = '%d' and semesterNumber = '%d'",c.getWorkload().getWorkloadId(),c.getSemesterNumber());
        System.out.println(q);
        WorkloadEntry ck;
        try
        {
            ck = (WorkloadEntry)em.createQuery(q).getSingleResult();
        }
        catch (NoResultException nr) {
            ck = c;
            em.persist(ck);
            em.getTransaction().commit();
        }
        catch (NonUniqueResultException nur) {
            ck = (WorkloadEntry)em.createQuery(q).getResultList().get(0);
        }
        return ck;
    }

}
