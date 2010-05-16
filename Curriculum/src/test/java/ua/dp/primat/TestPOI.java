/*
 *  Copyright 2010 Acer.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package ua.dp.primat;

import java.io.IOException;
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
import ua.dp.primat.curriculum.data.IndividualControl;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.WorkloadEntry;
import ua.dp.primat.curriculum.planparser.PlanParser;
import static org.junit.Assert.*;

/**
 *
 * @author Acer
 */
public class TestPOI {

    public TestPOI() {
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
    public void testIt() {
        StudentGroup sgg = new StudentGroup("PZ", new Long(1), new Long(2008));
        PlanParser pp = new PlanParser("src/test/resources/PZ_B.07_08_140307_lev4.xls", sgg, 0, 8, 83);
        try
        {
            pp.Parse();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        //list the db
        long cItems = 0;
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("curriculum");
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        List caths = em.createQuery("from WorkloadEntry").getResultList();
        System.out.println( caths.size() + " item(s) found:" );

        for ( Iterator iter = caths.iterator(); iter.hasNext(); ) {
            cItems++;
            WorkloadEntry c = (WorkloadEntry)iter.next();
            System.out.println( c.getWorkload().getDiscipline().getName() +
                    "|" + c.getSemesterNumber() +
                    "|" + c.getCourceWork().toString() +
                    "|" + c.getFinalControl().toString() +
                    "| size:" + c.getIndividualControl().size());
        }
        //em.getTransaction().commit();
        em.close();

        //check result
        assertEquals(cItems > 50,true);

    }

}