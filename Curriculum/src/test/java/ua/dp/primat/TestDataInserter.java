/*
 *  Test unit for testing the DataInserter object
 */

package ua.dp.primat;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.dp.primat.curriculum.data.Cathedra;
import ua.dp.primat.curriculum.data.Discipline;
import ua.dp.primat.curriculum.utils.DataInserter;
import static org.junit.Assert.*;

/**
 *
 * @author Acer
 */
public class TestDataInserter {

    public TestDataInserter() {
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
        List l = em.createQuery("from Cathedra").getResultList();
        long cWas = l.size();
        em.getTransaction().commit();
        //logic
        em.getTransaction().begin();

        Cathedra pmm = new Cathedra();
        pmm.setName("PMMK");
        pmm = DataInserter.addCathedra(em, pmm);

        Cathedra pmm2 = new Cathedra();
        pmm2.setName("PMMK");
        pmm2 = DataInserter.addCathedra(em, pmm2);

        if (!em.getTransaction().isActive())
            em.getTransaction().begin();

        Discipline subj1 = new Discipline();
        subj1.setName("MathAn");
        subj1.setCathedra(pmm);
        em.persist(subj1);

        Discipline subj2 = new Discipline();
        subj2.setName("Algebra");
        subj2.setCathedra(pmm2);
        em.persist(subj2);
        //commit
        em.getTransaction().commit();
        //em.close();

        //run test
        //list the db
        long cathItems = 0;
        em.getTransaction().begin();
        List caths = em.createQuery("from Cathedra").getResultList();
        System.out.println( caths.size() + " cathedra(s) found:" );

        for ( Iterator iter = caths.iterator(); iter.hasNext(); ) {
            cathItems++;
            Cathedra c = (Cathedra)iter.next();
            System.out.println( c.getCathedraId() + "|" + c.getName() );
        }
        //em.getTransaction().commit();
        em.close();

        //check result
        assertEquals(1, cathItems-cWas);
    }

}