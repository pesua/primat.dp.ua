package ua.dp.primat;

import java.io.IOException;
import junit.framework.TestCase;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.planparser.PlanParser;

/**
 *
 * @author Acer
 */
public class TestDB extends TestCase {
    
    public TestDB(String testName) {
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

    public void testDB() {
        StudentGroup sgroup = new StudentGroup();
        sgroup.setCode("ПЗ");
        sgroup.setYear(new Long(2007));
        sgroup.setNumber(new Long(1));
        PlanParser pp = new PlanParser("D:/Education/dnu/2kurs/Exigen/Content/plans/PZ_B.07_08_140307_lev4.xls", sgroup, 0, 8, 84);
        try {
            pp.Parse();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }

        assertEquals(1, 1);
    }
}
