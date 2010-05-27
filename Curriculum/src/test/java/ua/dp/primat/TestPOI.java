package ua.dp.primat;

import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.planparser.CurriculumParser;
import ua.dp.primat.curriculum.planparser.CurriculumXLSRow;
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
        int semesters = 8;
        StudentGroup pz081 = new StudentGroup("PZ", new Long(1), new Long(2008));
        List<CurriculumXLSRow> listParsed = null;
        try {
            CurriculumParser cParser = new CurriculumParser(pz081, 0, 8, 83, semesters,
                "src/test/resources/PZ_B.07_08_140307_lev4.xls");
            listParsed = cParser.parse();
        }
        catch (IOException ioe) {
            System.out.println("Parser test error: " + ioe);
        }
        for (int i=0;i<listParsed.size();i++) {
            System.out.println(listParsed.get(i).toString());
        }

        //check result
        assertEquals(String.format("We get extacly %d entries",listParsed.size()),true,listParsed.size() > 50);
    }

}