package ua.dp.primat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.curriculum.planparser.CurriculumParser;
import ua.dp.primat.curriculum.planparser.CurriculumXLSRow;
import ua.dp.primat.domain.workload.LoadCategory;
import ua.dp.primat.domain.workload.Workload;
import ua.dp.primat.domain.workload.WorkloadType;
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
        StudentGroup pm082 = new StudentGroup("PM", new Long(2), new Long(2008));
        List<CurriculumXLSRow> listParsed = null;
        try {
            CurriculumParser cParser = new CurriculumParser(pm082, 0, 8, 89, semesters,
                "src/test/resources/PM_Bachelor_0708_2.xls");
            listParsed = cParser.parse();
        }
        catch (IOException ioe) {
            System.out.println("Parser test error: " + ioe);
        }
        for (int i=0;i<listParsed.size();i++) {
            System.out.println(rowToString(listParsed.get(i)));
        }

        //check result
        assertEquals(String.format("We get extacly %d entries",listParsed.size()),true,listParsed.size() > 50);
    }

    private String rowToString(CurriculumXLSRow xlsRow) {
        StringBuilder result = new StringBuilder();
        final String eolChar = String.format("%n");

        result.append("Discipline: ").append(xlsRow.getDiscipline().getName()).append(eolChar);
        
        for (Workload w : xlsRow.getWorkloadList()) {
            result.append("Category: ").append(w.getLoadCategory()).append(eolChar);
            result.append("Type: "+w.getType() + eolChar);
            result.append("-> Semester:"+w.getSemesterNumber()
                    + "| FinalControl:" + w.getFinalControlType()
                    + "| CourseWork:" + w.getCourseWork()
                    + "| IndividualControlCount:" + w.getIndividualControl().size()
                    + eolChar);

            for (int k=0;k<w.getIndividualControl().size();k++) {
                result.append("---> IndividualControl: " + w.getIndividualControl().get(k).getType());
                result.append(", " + w.getIndividualControl().get(k).getWeekNum());
                result.append(eolChar);
            }
        }

        return result.toString();
    }

}