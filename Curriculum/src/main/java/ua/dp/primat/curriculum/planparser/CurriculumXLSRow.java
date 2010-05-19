/*
 *  
 */

package ua.dp.primat.curriculum.planparser;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Vector;
import ua.dp.primat.curriculum.data.FinalControlType;
import ua.dp.primat.curriculum.data.LoadCategory;
import ua.dp.primat.curriculum.data.WorkloadType;

/**
 *
 * @author fdevelop
 */
public class CurriculumXLSRow {

    private static final char DIFFERENTIAL_TEST_MARK = '�';

    private int[] parseNumValues(String fmStr, boolean standard) {
        Vector<Integer> intValues = new Vector<Integer>();
        String[] values = fmStr.split(",");

        for (int i=0;i<values.length;i++)
            if (standard) {
                try {
                    intValues.add((int)Double.parseDouble(values[i].trim()));
                }
                catch (NumberFormatException nfe) { }
            } else {
                if (values[i].indexOf(DIFFERENTIAL_TEST_MARK) > -1) {
                    String valueWithoutMark = values[i].replaceAll(String.valueOf(DIFFERENTIAL_TEST_MARK), "").trim();
                    try {
                        intValues.add((int)Double.parseDouble(valueWithoutMark.trim()));
                    }
                    catch (NumberFormatException nfe) { }
                }
            }

        int[] result = new int[intValues.size()];
        for (int i=0;i<intValues.size();i++)
            result[i] = intValues.get(i);
        return result;
    }

    private List<IndividualControlEntry> createIndividualWorkList(String siwSemester, String siwForm, String siwWeek) {
        int[] Semesters = parseNumValues(siwSemester,true);
        String[] Types = siwForm.trim().split(",");
        int[] Weeks = parseNumValues(siwWeek,true);

        if ((Semesters.length != Types.length) || (Semesters.length != Weeks.length))
            return null;

        Vector<IndividualControlEntry> entries = new Vector<IndividualControlEntry>();
        for (int i=0;i<Semesters.length;i++)
        {
            IndividualControlEntry ic = new IndividualControlEntry();
            ic.setSemester(Semesters[i]);
            ic.setType(Types[i]);
            ic.setWeekNum(Weeks[i]);
            entries.add(ic);
        }

        return entries;
    }

    public CurriculumXLSRow(String disciplineName, String cathedraName,
                            String sfmExams, String sfmTests, String sfmCourses,
                            String siwSemester, String siwForm, String siwWeek,
                            Map<Integer, WorkHours> SemesterHours,
                            WorkloadType workloadType, LoadCategory loadCategory) {
        this.disciplineName = disciplineName;
        this.cathedraName = cathedraName;

        this.fmExams = parseNumValues(sfmExams, true);
        Arrays.sort(fmExams);
        this.fmTests = parseNumValues(sfmTests, true);
        Arrays.sort(fmTests);
        this.fmCourses = parseNumValues(sfmCourses, true);
        Arrays.sort(fmCourses);
        this.fmDifTests = parseNumValues(sfmTests, false);
        Arrays.sort(fmDifTests);

        this.indWorks = createIndividualWorkList(siwSemester, siwForm, siwWeek);
        
        this.HoursForSemesters = SemesterHours;

        this.workloadType = workloadType;
        this.loadCategory = loadCategory;
    }

    private String disciplineName;
    private String cathedraName;
    private WorkloadType workloadType;
    private LoadCategory loadCategory;
    //final monitoring
    private int[] fmExams;
    private int[] fmDifTests;
    private int[] fmTests;
    private int[] fmCourses;
    //individual works
    private List<IndividualControlEntry> indWorks;
    //hours info
    private Map<Integer ,WorkHours> HoursForSemesters;

    /* getters and setters */

    /**
     * Method, that returns an information about planned course work for specified semester.
     * @param semester
     * @return true - if there is a course work in specified semester
     */
    public boolean getCourseInSemester(int semester) {
        return (Arrays.binarySearch(fmCourses, semester) != -1);
    }

    /**
     * Method, that returns an information about final control type for specified semester.
     * @param semester
     * @return Value of FinalControlType, which indicates a type of final control for specified semester
     */
    public FinalControlType getFinalControlTypeInSemester(int semester) {
        if (Arrays.binarySearch(fmExams, semester) > -1) {
            return FinalControlType.Exam;
        } else if (Arrays.binarySearch(fmTests, semester) > -1) {
            return FinalControlType.Setoff;
        } else if (Arrays.binarySearch(fmDifTests, semester) > -1) {
            return FinalControlType.DifferentiableSetoff;
        } else {
            return FinalControlType.Nothing;
        }
    }

    public String getCathedraName() {
        return cathedraName;
    }

    public void setCathedraName(String cathedraName) {
        this.cathedraName = cathedraName;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public LoadCategory getLoadCategory() {
        return loadCategory;
    }

    public void setLoadCategory(LoadCategory loadCategory) {
        this.loadCategory = loadCategory;
    }

    public WorkloadType getWorkloadType() {
        return workloadType;
    }

    public void setWorkloadType(WorkloadType workloadType) {
        this.workloadType = workloadType;
    }
}
