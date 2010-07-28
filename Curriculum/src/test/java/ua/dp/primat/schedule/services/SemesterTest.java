package ua.dp.primat.schedule.services;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.dp.primat.domain.StudentGroup;
import static org.junit.Assert.*;

/**
 *
 * @author EniSh
 */
public class SemesterTest {

    public SemesterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of toString method, of class Semester.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Semester instance = new Semester(2007, 1);
        String expResult = "2007-2008, 1 semester";
        String result = instance.toString();
        assertEquals("Incorrect work of method toString", expResult, result);
    }

    /**
     * Test of equals method, of class Semester.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Semester instance = new Semester(new StudentGroup("", 1L, 2008L), 3);
        Semester instance1 = new Semester(2009, 1);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        obj = new String();
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);
        expResult = true;
        result = instance.equals(instance1);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class Semester.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Semester instance = new Semester(2008, 1);
        int expResult = 97570;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

}