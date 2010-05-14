package ua.dp.primat.curriculum.data;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Acer
 */
public class RecordManager {

    public static void addCathedra(Cathedra cathedra) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(cathedra);
            transaction.commit();
        }
        finally {
            session.close();
        }
    }

    public static void addDiscipline(Discipline discipline) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(discipline);
            transaction.commit();
        }
        finally {
            session.close();
        }
    }

    public static void addWorkload(Workload workload) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(workload);
            transaction.commit();
        }
        finally {
            session.close();
        }
    }

    public static void addStudentGroup(StudentGroup studentGroup) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(studentGroup);
            transaction.commit();
        }
        finally {
            session.close();
        }
    }

    public static void addWorkloadEntry(WorkloadEntry workloadentry) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(workloadentry);
            transaction.commit();
        }
        finally {
            session.close();
        }
    }

    public static void addIndividualControl(IndividualControl individualControl) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(individualControl);
            transaction.commit();
        }
        finally {
            session.close();
        }
    }

}
