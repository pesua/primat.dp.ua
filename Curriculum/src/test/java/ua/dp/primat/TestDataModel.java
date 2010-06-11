package ua.dp.primat;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ua.dp.primat.domain.Cathedra;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.domain.workload.FinalControlType;
import ua.dp.primat.domain.workload.IndividualControl;
import ua.dp.primat.domain.workload.LoadCategory;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.workload.Workload;
import ua.dp.primat.domain.workload.WorkloadEntry;
import ua.dp.primat.domain.workload.WorkloadType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Acer
 */
public class TestDataModel {

    public TestDataModel() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void hello() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("curriculum");
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        List l = em.createQuery("from IndividualControl").getResultList();
        long cWas = l.size();
        em.getTransaction().commit();

        //logic
        em.getTransaction().begin();

        StudentGroup sg = new StudentGroup("PZ", new Long(1), new Long(2008));
        em.persist(sg);

        Cathedra pmz = new Cathedra();
        pmz.setName("PMZ");
        em.persist(pmz);

        Discipline subj1 = new Discipline();
        subj1.setName("Programming");
        subj1.setCathedra(pmz);
        em.persist(subj1);

        Workload wl = new Workload();
        wl.setDiscipline(subj1);
        wl.setLoadCategory(LoadCategory.Normative);
        wl.setType(WorkloadType.wtProfPract);
        wl.getGroups().add(sg);
        em.persist(wl);

        WorkloadEntry wle = new WorkloadEntry();
        wle.setWorkload(wl);
        wle.setSemesterNumber(new Long(2));
        wle.setCourceWork(Boolean.TRUE);
        wle.setFinalControl(FinalControlType.Exam);
        wle.setIndCount(new Long(3));
        wle.setLabCount(new Long(3));
        wle.setLectionCount(new Long(3));
        wle.setPracticeCount(new Long(3));
        em.persist(wle);

        IndividualControl ic = new IndividualControl();
        ic.setWorkloadEntry(wle);
        ic.setType("kr");
        ic.setWeekNum(new Long(17));
        em.persist(ic);

        IndividualControl ic2 = new IndividualControl();
        ic2.setWorkloadEntry(wle);
        ic2.setType("AO");
        ic2.setWeekNum(new Long(9));
        em.persist(ic2);

        //commit
        em.getTransaction().commit();
        //em.close();

        //run test
        //list the db
        long cItems = 0;
        em.getTransaction().begin();
        List caths = em.createQuery("from IndividualControl").getResultList();
        System.out.println( caths.size() + " item(s) found:" );

        for ( Iterator iter = caths.iterator(); iter.hasNext(); ) {
            cItems++;
            IndividualControl c = (IndividualControl)iter.next();
            System.out.println( c.getWorkloadEntry().getWorkload().getDiscipline().getName() +
                    "|" + c.getWorkloadEntry().getSemesterNumber() +
                    "|" + c.getType() +
                    "|" + c.getWeekNum());
        }
        //em.getTransaction().commit();
        em.close();

        //check result
        assertEquals(2, cItems-cWas);
    }

}