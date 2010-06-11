package ua.dp.primat.curriculum.planparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import ua.dp.primat.domain.workload.FinalControlType;
import ua.dp.primat.domain.workload.LoadCategory;
import ua.dp.primat.domain.workload.WorkloadType;
import ua.dp.primat.domain.Cathedra;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.domain.workload.WorkloadOld;
import ua.dp.primat.domain.workload.WorkloadEntry;
import ua.dp.primat.domain.workload.IndividualControl;
import ua.dp.primat.domain.StudentGroup;

/**
 * Object, which represents the one curriculum row and
 * this one has methods to create database entities.
 * @author fdevelop
 */
public final class CurriculumXLSRow {

    //PRIVATE Variables
    private String diffSetOff;
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
    private Map<Integer, WorkHours> hoursForSemesters;

    //DataBase output objects
    private StudentGroup group;
    private Cathedra cathedra;
    private Discipline discipline;
    private WorkloadOld workload;

    /**
     * Constructor, that gets atomic info from one row and creates data entities
     * objects (Cathedra, Discipline, WorkloadOld).
     * @param group
     * @param disciplineName
     * @param cathedraName
     * @param sfmExams
     * @param sfmTests
     * @param sfmCourses
     * @param siwSemester
     * @param siwForm
     * @param siwWeek
     * @param semesterHours
     * @param workloadType
     * @param loadCategory
     * @param diffSetOff
     */
    public CurriculumXLSRow(StudentGroup group, String disciplineName, String cathedraName,
                            String sfmExams, String sfmTests, String sfmCourses,
                            String siwSemester, String siwForm, String siwWeek,
                            Map<Integer, WorkHours> semesterHours,
                            WorkloadType workloadType, LoadCategory loadCategory,
                            String diffSetOff) {
        this.diffSetOff = diffSetOff;

        this.disciplineName = disciplineName;
        this.cathedraName = cathedraName;
        this.workloadType = workloadType;
        this.loadCategory = loadCategory;

        this.fmExams = parseNumValues(sfmExams, true);
        Arrays.sort(fmExams);
        this.fmTests = parseNumValues(sfmTests, true);
        Arrays.sort(fmTests);
        this.fmCourses = parseNumValues(sfmCourses, true);
        Arrays.sort(fmCourses);
        this.fmDifTests = parseNumValues(sfmTests, false);
        Arrays.sort(fmDifTests);

        this.indWorks = createIndividualWorkList(siwSemester, siwForm, siwWeek);
        this.hoursForSemesters = semesterHours;

        this.group = group;

        //generate database entries
        generateDatabaseEntries();
    }

    /**
     * toString() overriden method for plain representation of the parsed item.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        final String eolChar = String.format("%n");

        result.append("Discipline: "+this.getDiscipline().getName() + eolChar);
        result.append("Category: "+this.getWorkload().getLoadCategory() + eolChar);
        result.append("Type: "+this.getWorkload().getType() + eolChar);
        for (int j=0;j<this.getWorkload().getEntries().size();j++) {
            result.append("-> Semester:"+this.getWorkload().getEntries().get(j).getSemesterNumber()
                    + "| FinalControl:" + this.getWorkload().getEntries().get(j).getFinalControl()
                    + "| CourseWork:" + this.getWorkload().getEntries().get(j).getCourceWork()
                    + "| IndividualControlCount:" + this.getWorkload().getEntries().get(j).getIndividualControl().size()
                    + eolChar);

            for (int k=0;k<this.getWorkload().getEntries().get(j).getIndividualControl().size();k++) {
                result.append("---> IndividualControl: " + this.getWorkload().getEntries().get(j).getIndividualControl().get(k).getType());
                result.append(", " + this.getWorkload().getEntries().get(j).getIndividualControl().get(k).getWeekNum());
                result.append(eolChar);
            }
        }

        return result.toString();
    }

    /* getters */
    
    public Cathedra getCathedra() {
        return cathedra;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public WorkloadOld getWorkload() {
        return workload;
    }

    /**
     * Parses a string like '1,2  ,3, 7', which is used in Curriculum. There is a
     * special case for Setoffs: it could be as '1, 2, 3d, 4', where 'd' means
     * differential setoff (this char is defined by the diffSetOff language constant).
     * @param fmStr - the input string
     * @param standard - if true, a test of differential setoff will be skipped
     * @return The generated array of parsed integers. If standard was false, the result
     *      will include only marked numbers (like '3d').
     */
    private int[] parseNumValues(String fmStr, boolean standard) {
        final List<Integer> intValues = new ArrayList<Integer>();
        final String[] values = fmStr.split(",");

        String valueWithoutMark;
        for (int i=0;i<values.length;i++) {
            valueWithoutMark = values[i].trim();
            if (!standard) {
                if (valueWithoutMark.indexOf(diffSetOff) > -1) {
                    valueWithoutMark = valueWithoutMark.replaceAll(diffSetOff, "");
                }
            }
            try {
                intValues.add((int)Double.parseDouble(valueWithoutMark));
            }
            catch (NumberFormatException nfe) {
                //could not be parsed, ignore it
            }
        }

        final int[] result = new int[intValues.size()];
        for (int i=0;i<intValues.size();i++) {
            result[i] = intValues.get(i);
        }
        return result;
    }

    /**
     * Parses the individual control strings like 'AO, 2mw' and returns
     * an array of token like ['AO','mw','mw'].
     * @param individualControlTypeCell - the input string
     * @return The string array of tokens
     */
    private String[] parseIndividualControlTypes(String individualControlTypeCell) {
        final List<String> listTokens = new ArrayList<String>();
        
        //value, that contains all tokens separated by coma. This value lose one token after one iteration
        String workIndForm = individualControlTypeCell.trim();
        //one received token
        String tokenType;

        while (workIndForm.length() > 0) {
            if (workIndForm.indexOf(',') > -1) {
                tokenType = workIndForm.substring(0, workIndForm.indexOf(',')).trim();
            } else {
                tokenType = workIndForm.trim();
                workIndForm = "";
            }

            if (tokenType.length() > 0) {
                if ((tokenType.charAt(0) >= '0') && (tokenType.charAt(0) <= '9')) {
                    final int nextWorksCount = Integer.parseInt(tokenType.substring(0,1));
                    for (int y=0;y<nextWorksCount;y++) {
                        listTokens.add(tokenType.substring(1));
                    }
                } else {
                    listTokens.add(tokenType);
                }
            }

            if (workIndForm.indexOf(',') > -1) {
                workIndForm = workIndForm.substring(workIndForm.indexOf(',')+1);
            }
        }

        return listTokens.toArray(new String[0]);
    }

    /**
     * Parses 3 columns for individual controls works info. It includes semester number,
     * type of control work and its week number of year. If the count of tokens
     * in parameters are different, method returns null.
     *
     * @param siwSemester - cell text for semester number of work
     * @param siwForm - cell text for type of control work
     * @param siwWeek - cell text for number of week
     * @return The array of IndividualControlEntry objects or null, if count of tokens
     *      are different.
     */
    private List<IndividualControlEntry> createIndividualWorkList(String siwSemester, String siwForm, String siwWeek) {
        final int[] semesters = parseNumValues(siwSemester,true);
        final String[] types = parseIndividualControlTypes(siwForm);
        final int[] weeks = parseNumValues(siwWeek,true);

        if ((semesters.length != types.length) || (semesters.length != weeks.length)) {
            return null;
        }

        final List<IndividualControlEntry> entries = new ArrayList<IndividualControlEntry>();
        for (int i=0;i<semesters.length;i++) {
            entries.add(new IndividualControlEntry(semesters[i], types[i], weeks[i]));
        }

        return entries;
    }

    /**
     * Method, that returns an information about planned course work for specified semester.
     * @param semester
     * @return true - if there is a course work in specified semester
     */
    private boolean getCourseInSemester(int semester) {
        return (Arrays.binarySearch(fmCourses, semester) != -1);
    }

    /**
     * Method, that returns an information about final control type for specified semester.
     * @param semester
     * @return Value of FinalControlType, which indicates a type of final control for specified semester
     */
    private FinalControlType getFinalControlTypeInSemester(int semester) {
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

    /**
     * Creates new entity Cathedra.
     */
    private void generateCathedra() {
        cathedra = new Cathedra();
        cathedra.setName(cathedraName);
    }

    /**
     * Creates new entity Discipline for specified Cathedra.
     * @param cathedra
     */
    private void generateDiscipline(Cathedra cathedra) {
        discipline = new Discipline();
        discipline.setName(disciplineName);
        discipline.setCathedra(cathedra);
    }

    /**
     * Creates new entity WorkloadOld for specified Discipline.
     * @param discipline
     */
    private void generateWorkload(Discipline discipline) {
        workload = new WorkloadOld();
        workload.setDiscipline(discipline);
        workload.setLoadCategory(loadCategory);
        workload.setType(workloadType);
        workload.getGroups().add(group);
        //group.getWorkloads().add(workload);
    }

    /**
     * Generates list of IndividualControls for specified WorkloadEntry.
     * @param workloadEntry
     */
    private void generateIndividualControls(WorkloadEntry workloadEntry) {
        for (IndividualControlEntry ice : indWorks) {
            if (workloadEntry.getSemesterNumber() == ice.getSemester()) {
                final IndividualControl ic = new IndividualControl(ice.getType(),
                        Long.valueOf(ice.getWeekNum()));
                workloadEntry.getIndividualControl().add(ic);
            }
        }
    }

    /**
     * Generates list of WorkloadEntry for specified WorkloadOld.
     * @param workload
     */
    private void generateWorkloadEntries(WorkloadOld workload) {
        for (Integer i : hoursForSemesters.keySet()) {
            if (hoursForSemesters.get(i) != null) {
                final WorkloadEntry workloadEntry = new WorkloadEntry();
                workloadEntry.setCourceWork(getCourseInSemester(i));
                workloadEntry.setFinalControl(getFinalControlTypeInSemester(i));
                workloadEntry.setIndCount(Long.valueOf(Math.round(hoursForSemesters.get(i).getHoursSam()
                        + hoursForSemesters.get(i).getHoursInd())));
                workloadEntry.setLabCount(Long.valueOf(Math.round(hoursForSemesters.get(i).getHoursLab())));
                workloadEntry.setLectionCount(Long.valueOf(Math.round(hoursForSemesters.get(i).getHoursLec())));
                workloadEntry.setPracticeCount(Long.valueOf(Math.round(hoursForSemesters.get(i).getHoursPract())));
                workloadEntry.setSemesterNumber(Long.valueOf(i));
                workload.getEntries().add(workloadEntry);
                workloadEntry.setParentWorkload(workload);
                //individual controls
                generateIndividualControls(workloadEntry);
            }
        }
    }

    /**
     * Executes the generation methods for database objects.
     */
    private void generateDatabaseEntries() {
        generateCathedra();
        generateDiscipline(cathedra);
        generateWorkload(discipline);
        generateWorkloadEntries(workload);
    }
}