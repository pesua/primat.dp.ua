package ua.dp.primat.curriculum.planparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import ua.dp.primat.curriculum.data.Cathedra;
import ua.dp.primat.curriculum.data.Discipline;
import ua.dp.primat.curriculum.data.FinalControlType;
import ua.dp.primat.curriculum.data.IndividualControl;
import ua.dp.primat.curriculum.data.LoadCategory;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.Workload;
import ua.dp.primat.curriculum.data.WorkloadEntry;
import ua.dp.primat.curriculum.data.WorkloadType;
import ua.dp.primat.curriculum.utils.DataInserter;

/**
 *
 * @author fdevelop
 */
public class PlanParser {

    public PlanParser(String fileName, StudentGroup sg, int sheet, int start, int end) {
        this.fileName = fileName;
        this.sheet_num = sheet;
        this.id_start = start;
        this.id_end = end;
        this.group = sg;

        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("curriculum");
        entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(this.group);

    }

    public void Parse() throws IOException {
        
        InputStream fios = null;

        try {
            //code start
            fios = new FileInputStream(fileName);
            HSSFWorkbook wb = new HSSFWorkbook(fios);
            HSSFSheet sheet = wb.getSheetAt(sheet_num);
            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

            WorkloadType wtype = WorkloadType.wtProfPract;
            LoadCategory lcat = LoadCategory.Normative;

            int rows = sheet.getPhysicalNumberOfRows();
            for (int r = id_start; r <= id_end; r++) {
                HSSFRow row = sheet.getRow(r);
                
                if (row == null) continue;

                int cells = row.getPhysicalNumberOfCells();

                if (row.getCell(0) == null)
                    continue;

                if (row.getCell(0).toString().compareTo("") == 0)
                    continue;

                if ((row.getCell(1) == null) || (row.getCell(1).toString().compareTo("") == 0)) {
                    HSSFCell cell = row.getCell(0);
                    if ( cell.getStringCellValue().indexOf(". ") > -1 ) {
                        int dtype = Integer.parseInt( cell.getStringCellValue().substring(0, cell.getStringCellValue().indexOf(".")) );
                        switch (dtype) {
                            case 1:wtype = WorkloadType.wtHumanities;
                            case 2:wtype = WorkloadType.wtNaturalScience;
                            case 3:wtype = WorkloadType.wtProfPract;
                            case 4:wtype = WorkloadType.wtProfPractStudent;
                            case 5:wtype = WorkloadType.wtProfPractUniver;
                        }
                        lcat = LoadCategory.Normative;
                    }
                    else {
                        String categoryName = cell.getStringCellValue();
                        if (categoryName.equalsIgnoreCase("Альтернатива до військової підготовки"))
                            lcat = LoadCategory.AlternativeForWar;
                        else if (categoryName.equalsIgnoreCase("Вибіркова частина"))
                            lcat = LoadCategory.Selective;
                        else
                            lcat = LoadCategory.Normative;
                    }

                    continue;
                }

                //add new discipline and cathedra
                String subjName = row.getCell(colName).getStringCellValue();
                String subjCath = row.getCell(colCath).getStringCellValue();
                Cathedra cath = new Cathedra();
                cath.setName(subjCath);
                cath = DataInserter.addCathedra(entityManager, cath);

                Discipline disc = new Discipline();
                disc.setName(subjName);
                disc.setCathedra(cath);
                disc = DataInserter.addDiscipline(entityManager, disc);

                double workPGpract = row.getCell(colPG).getNumericCellValue();
                double workPGlab = row.getCell(colPG+1).getNumericCellValue();

                Workload wload = new Workload();
                wload.setDiscipline(disc);
                wload.setLoadCategory(lcat);
                wload.setType(wtype);
                List<StudentGroup> lsg = wload.getGroups();
                lsg.add(group);
                wload.setGroups(lsg);
                wload = DataInserter.addWorkload(entityManager, wload);

                //internal ind. control
                String workIndSem = row.getCell(colIndTask).toString();
                String workIndForm = row.getCell(colIndTask+1).toString();
                String workIndWeek = row.getCell(colIndTask+2).toString();
                Vector<Vector<IndividualControl>> cw = new Vector<Vector<IndividualControl>>(8);
                for (int y=0;y<csemCount;y++) {
                    cw.add(new Vector<IndividualControl>());
                }

                if (workIndSem.compareTo("") != 0) {
                    String[] qwis = workIndSem.split(",");
                    String[] qwiw = workIndWeek.split(",");
                    Vector<Integer> wis = new Vector<Integer>();
                    for (int y=0;y<qwis.length;y++) wis.add((int)Math.round(Double.parseDouble(qwis[y])-1));
                    Vector<Integer> wiw = new Vector<Integer>();
                    for (int y=0;y<qwiw.length;y++) wiw.add((int)Math.round(Double.parseDouble(qwiw[y])));
                    Vector<String> wif = new Vector<String>();
                    while (workIndForm.indexOf(",") > -1) {
                        String krform = workIndForm.substring(0, workIndForm.indexOf(",")).trim();
                        if (krform.isEmpty()) continue;
                        if ((krform.charAt(0) >= '0') && (krform.charAt(0) <= '9')) {
                            int krcount = Integer.parseInt(krform.substring(0,1));
                            for (int y=0;y<krcount;y++)
                                wif.add(krform.substring(1));
                        } else
                            wif.add(krform);
                        workIndForm = workIndForm.substring(workIndForm.indexOf(",")+1);
                    }
                    String krform = workIndForm.trim();
                    if ((krform.charAt(0) >= '0') && (krform.charAt(0) <= '9')) {
                        int krcount = Integer.parseInt(krform.substring(0,1));
                        for (int y=0;y<krcount;y++)
                            wif.add(krform.substring(1));
                    } else
                        wif.add(krform);

                    for (int y=0;y<wis.size();y++) {
                        IndividualControl ic = new IndividualControl();
                        ic.setType(wif.elementAt(y));
                        ic.setWeekNum(new Long(wiw.elementAt(y)));
                        cw.elementAt(wis.elementAt(y)).add(ic);
                    }
                }

                //entry
                String workCtrlExams = row.getCell(colControl).toString();
                String workCtrlMark = row.getCell(colControl+1).toString();
                String workCtrlCourse = row.getCell(colControl+2).toString();
                Semester[] sc = new Semester[csemCount];
                for (int sem=0; sem < sc.length; sem++) {
                    sc[sem] = new Semester();
                    Integer rs = new Integer(sem+1);
                    if (workCtrlExams.indexOf(rs.toString()) > -1)
                        sc[sem].fcType = FinalControlType.Exam;
                    else if (workCtrlMark.indexOf(rs.toString()) > -1) {
                        int fid = workCtrlMark.indexOf(rs.toString());
                        if ((workCtrlMark.length() == fid+1) || (workCtrlMark.charAt(fid+1) != 'д'))
                            sc[sem].fcType = FinalControlType.Setoff;
                        else
                            sc[sem].fcType = FinalControlType.DifferentiableSetoff;
                    } else
                        sc[sem].fcType = FinalControlType.Nothing;

                    sc[sem].isCourse = (workCtrlCourse.indexOf(rs.toString()) > -1);

                    sc[sem].workHours.hLec = row.getCell(colHoursLec+6*sem).getNumericCellValue();
                    sc[sem].workHours.hPract = row.getCell(colHoursPract+6*sem).getNumericCellValue();
                    sc[sem].workHours.hLab = row.getCell(colHoursLab+6*sem).getNumericCellValue();
                    sc[sem].workHours.hInd = row.getCell(colHoursInd+6*sem).getNumericCellValue();
                    sc[sem].workHours.hSam = row.getCell(colHoursSam+6*sem).getNumericCellValue();
                    //---
                    if (sc[sem].workHours.getSum() > 0) {
                        WorkloadEntry wlEntry = new WorkloadEntry();
                        wlEntry.setWorkload(wload);
                        wlEntry.setCourceWork(sc[sem].isCourse);
                        wlEntry.setFinalControl(sc[sem].fcType);
                        wlEntry.setIndCount(Math.round(sc[sem].workHours.hInd+sc[sem].workHours.hSam));
                        wlEntry.setLabCount(Math.round(sc[sem].workHours.hLab));
                        wlEntry.setLectionCount(Math.round(sc[sem].workHours.hLec));
                        wlEntry.setPracticeCount(Math.round(sc[sem].workHours.hPract));
                        wlEntry.setSemesterNumber(new Long(sem+1));
                        wlEntry = DataInserter.addWorkloadEntry(entityManager, wlEntry);

                        for (int y=0;y<cw.get(sem).size();y++) {
                            if (!entityManager.getTransaction().isActive())
                                entityManager.getTransaction().begin();
                            IndividualControl cwic = new IndividualControl();
                            cwic.setType(cw.get(sem).get(y).getType());
                            cwic.setWeekNum(cw.get(sem).get(y).getWeekNum());
                            cwic.setWorkloadEntry(wlEntry);
                            //TODO: add individual control
                            entityManager.persist(cwic);
                            entityManager.getTransaction().commit();
                        }
                    }
                }
            }

            //entityManager.getTransaction().commit();
        }
        catch (FileNotFoundException ioe) {
            ioe.printStackTrace();
        }
        finally {
            entityManager.close();
            if (fios != null)
                fios.close();
        }

    }

    /* VARIABLES */
    EntityManager entityManager;

    private String fileName;
    //pre-params
    StudentGroup group;

    //default params for parsing
    int sheet_num;
    int id_start;
    int id_end;

    /* CONSTANTS */
    //count of semestrs for this plan
    public static final int csemCount = 8;

    //Excel index for Name of Discipline
    public static final int colName = 1;
    //Excel index for Cathedra of Discipline
    public static final int colCath = 2;
    //Excel index for PG count
    public static final int colPG = 3;
    //Excel index for type of final control (exam, mark, differential mark, course)
    public static final int colControl = 6;
    //Excel index for types of individual tasks
    public static final int colIndTask = 9;
    //Excel indexes for hours for the Discipline
    public static final int colHoursLec = 22;
    public static final int colHoursPract = 23;
    public static final int colHoursLab = 24;
    public static final int colHoursInd = 25;
    public static final int colHoursSam = 26;

}
