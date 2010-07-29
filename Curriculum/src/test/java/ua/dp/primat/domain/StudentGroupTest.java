/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.domain;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author EniSh
 */
public class StudentGroupTest {

    public StudentGroupTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testCreationGroupByFullCode() {
        System.out.println("CreationGroupByFullCode");
        StudentGroup instance = new StudentGroup("PZ-08-1");

        assertEquals("PZ", instance.getCode());
        assertEquals(Long.valueOf(2008), instance.getYear());
        assertEquals(Long.valueOf(1), instance.getNumber());
    }
}