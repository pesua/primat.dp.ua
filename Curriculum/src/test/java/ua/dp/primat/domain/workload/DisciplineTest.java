package ua.dp.primat.domain.workload;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ua.dp.primat.domain.Cathedra;

/**
 *
 * @author EniSh
 */
public class DisciplineTest {

    public DisciplineTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of equals method, of class Discipline.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        
        Discipline a = new Discipline("TestDisc", new Cathedra("TestCath"));
        assertFalse("Incorect compare with null", a.equals(null));

        Object i = 1;
        assertFalse("Incorect compare with Integer", a.equals(i));

        Discipline b = new Discipline("TestDisc1", new Cathedra("TestCath"));
        assertFalse("Incorect compare by null key", a.equals(b));

        Cathedra c = new Cathedra("");
        a.setName("trololo");
        a.setCathedra(c);
        b.setName("trololo");
        b.setCathedra(c);
        assertTrue("Incorect compare with same object", a.equals(b));

        assertTrue("Incorect compare with itself", a.equals(a));
    }

    /**
     * Test of hashCode method, of class Discipline.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Discipline instance = new Discipline();
        int expResult = 23273;
        int result = instance.hashCode();
        assertEquals("Incorect hash code", expResult, result);
        instance.setId(7L);
        expResult = 23532;
        result = instance.hashCode();
        assertEquals("Incorect hash code", expResult, result);
    }

    /**
     * Test of toString method, of class Discipline.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Discipline instance = new Discipline("TestDisc", new Cathedra("TestCath"));
        String expResult = "TestDisc (TestCath)";
        String result = instance.toString();
        assertEquals("Error: " + expResult + " != " + result, expResult, result);
    }
}