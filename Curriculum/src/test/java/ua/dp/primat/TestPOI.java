package ua.dp.primat;

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
        StudentGroup pz081 = new StudentGroup("ог", new Long(1), new Long(2008));
        CurriculumParser cParser = new CurriculumParser(pz081, 0, 8, 83, semesters,
                "src/test/resources/PZ_B.07_08_140307_lev4.xls");
        List<CurriculumXLSRow> listParsed = cParser.parse();
        for (int i=0;i<listParsed.size();i++) {
            System.out.println(listParsed.get(i).getDisciplineName());
            for (int j=1;j<=semesters;j++)
                System.out.print(">"+listParsed.get(i).getFinalControlTypeInSemester(j).toString());
            System.out.print("\n");
        }

        //check result
        assertEquals(true,listParsed.size() > 50);
    }

}