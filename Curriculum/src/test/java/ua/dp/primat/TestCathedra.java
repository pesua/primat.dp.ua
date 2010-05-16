/*
 *  This unit-test allows us to test the One-To-Many relation between
 *  tables Discipline and Cathedra.
 */

package ua.dp.primat;

import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ua.dp.primat.curriculum.data.Cathedra;
import ua.dp.primat.curriculum.data.Discipline;

/**
 *
 * @author fdevelop
 */
public class TestCathedra extends TestCase {
    
    public TestCathedra(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    //test for 2 disciplines, that has 1 cathedra
    public void test2Disciplines1Cathedra() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("curriculum");
        EntityManager em = emFactory.createEntityManager();
        //logic
        em.getTransaction().begin();

        Cathedra pmm = new Cathedra();
        pmm.setName("PMM");
        em.persist(pmm);

        Discipline subj1 = new Discipline();
        subj1.setName("MathAn");
        subj1.setCathedra(pmm);
        em.persist(subj1);

        Discipline subj2 = new Discipline();
        subj2.setName("Algebra");
        subj2.setCathedra(pmm);
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
        //assertEquals(1, cathItems);
    }

}
