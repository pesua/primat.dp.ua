package ua.dp.primat.curriculum.planparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import ua.dp.primat.curriculum.data.Cathedra;
import ua.dp.primat.curriculum.data.Discipline;
import ua.dp.primat.curriculum.data.FinalControlType;
import ua.dp.primat.curriculum.data.IndividualControl;
import ua.dp.primat.curriculum.data.LoadCategory;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.Workload;
import ua.dp.primat.curriculum.data.WorkloadEntry;
import ua.dp.primat.curriculum.data.WorkloadType;

/**
 *
 * @author fdevelop
 *
 */
public class CurriculumParser {

    private CurriculumParser(StudentGroup group, int sheetNum,
            int itemStart, int itemEnd, int semCount) {
        this.group = group;
        this.itemStart = itemStart;
        this.itemEnd = itemEnd;
        this.sheetNumber = sheetNum;
        this.semestersCount = semCount;
    }

    public CurriculumParser(StudentGroup group, int sheetNum,
            int itemStart, int itemEnd, int semCount, String fileName) {
        //run general constructor
        this(group, sheetNum, itemStart, itemEnd, semCount);

        File fileExcel = null;
        try {
            fileExcel = new File(fileName);

            this.excelBook = new HSSFWorkbook(new FileInputStream(fileExcel));
            currentSheet = excelBook.getSheetAt(sheetNumber);
        }
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public CurriculumParser(StudentGroup group, int sheetNum,
            int itemStart, int itemEnd, int semCount, Workbook workBook) {
        //run general constructor
        this(group, sheetNum, itemStart, itemEnd, semCount);
        
        this.excelBook = workBook;
        currentSheet = excelBook.getSheetAt(sheetNumber);
    }

    private CurriculumXLSRow ParseXLSEntry(Row row, WorkloadType workloadType, LoadCategory loadCategory) {
        if (row == null)
            return null;

        //parse info
        String subjName = row.getCell(COL_DISCIPLINE).getStringCellValue();
        String subjCath = row.getCell(COL_CATHEDRA).getStringCellValue();
        //entry
        String workCtrlExams = row.getCell(COL_FINALCONTROL).toString();
        String workCtrlMark = row.getCell(COL_FINALCONTROL+1).toString();
        String workCtrlCourse = row.getCell(COL_FINALCONTROL+2).toString();
        //internal ind. control
        String workIndSem = row.getCell(COL_INDIVIDUALTASKS).toString();
        String workIndForm = row.getCell(COL_INDIVIDUALTASKS+1).toString();
        String workIndWeek = row.getCell(COL_INDIVIDUALTASKS+2).toString();
        //hours
        Map<Integer, WorkHours> SemesterWorkhours = new HashMap<Integer, WorkHours>();
        for (int sem=0; sem < semestersCount; sem++) {
            WorkHours SemesterHoursInfo = new WorkHours();
            SemesterHoursInfo.hLec = row.getCell(COL_HOURS_LECTURE+6*sem).getNumericCellValue();
            SemesterHoursInfo.hPract = row.getCell(COL_HOURS_PRACTICE+6*sem).getNumericCellValue();
            SemesterHoursInfo.hLab = row.getCell(COL_HOURS_LAB+6*sem).getNumericCellValue();
            SemesterHoursInfo.hInd = row.getCell(COL_HOURS_INDIVIDUAL+6*sem).getNumericCellValue();
            SemesterHoursInfo.hSam = row.getCell(COL_HOURS_SELFWORK+6*sem).getNumericCellValue();
            if (SemesterHoursInfo.getSum() > 0)
                SemesterWorkhours.put(sem+1, null);
        }

        //finally, create object
        return new CurriculumXLSRow(subjName, subjCath,
                workCtrlExams, workCtrlMark, workCtrlCourse,
                workIndSem, workIndForm, workIndWeek, SemesterWorkhours,
                workloadType, loadCategory);
    }

    public List<CurriculumXLSRow> Parse() {
        WorkloadType wtype = WorkloadType.wtProfPract;
        LoadCategory lcat = LoadCategory.Normative;

        Vector<CurriculumXLSRow> entries = new Vector<CurriculumXLSRow>();

        int rows = currentSheet.getPhysicalNumberOfRows();
        for (int r = itemStart; r <= itemEnd; r++) {
            Row row = currentSheet.getRow(r);
            if (row == null)
                continue;

            if (row.getPhysicalNumberOfCells() == 0)
                continue;

            if (row.getCell(0).toString().isEmpty())
                continue;

            if ((row.getCell(1) == null) || (row.getCell(1).toString().isEmpty())) {
                Cell cell = row.getCell(0);
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
                    if (categoryName.equalsIgnoreCase(DISCIPLINE_CAT_ALTERNATIVEWAR))
                        lcat = LoadCategory.AlternativeForWar;
                    else if (categoryName.equalsIgnoreCase(DISCIPLINE_CAT_SELECTIVE))
                        lcat = LoadCategory.Selective;
                    else
                        lcat = LoadCategory.Normative;
                }

                continue;
            }

            entries.add(ParseXLSEntry(row, wtype, lcat));
        }

        return entries;
    }

    /* CONSTANTS */
    //
    public static final String DISCIPLINE_CAT_SELECTIVE = "Вибіркова частина";
    public static final String DISCIPLINE_CAT_ALTERNATIVEWAR = "Альтернатива до військової підготовки";

    //Excel index for Name of Discipline
    public static final int COL_DISCIPLINE = 1;
    //Excel index for Cathedra of Discipline
    public static final int COL_CATHEDRA = 2;
    //Excel index for type of final control (exam, mark, differential mark, course)
    public static final int COL_FINALCONTROL = 6;
    //Excel index for types of individual tasks
    public static final int COL_INDIVIDUALTASKS = 9;
    //Excel indexes for hours for the Discipline
    public static final int COL_HOURS_LECTURE = 22;
    public static final int COL_HOURS_PRACTICE = 23;
    public static final int COL_HOURS_LAB = 24;
    public static final int COL_HOURS_INDIVIDUAL = 25;
    public static final int COL_HOURS_SELFWORK = 26;

    /* VARIABLES */
    private Workbook excelBook;
    private Sheet currentSheet;

    //pre-params
    private StudentGroup group;
    private int sheetNumber = 0;
    private int itemStart = 8;
    private int itemEnd = 84;
    private int semestersCount = 8;


    //getters and setters
    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }

    public int getItemEnd() {
        return itemEnd;
    }

    public void setItemEnd(int itemEnd) {
        this.itemEnd = itemEnd;
    }

    public int getItemStart() {
        return itemStart;
    }

    public void setItemStart(int itemStart) {
        this.itemStart = itemStart;
    }

    public int getSemestersCount() {
        return semestersCount;
    }

    public void setSemestersCount(int semestersCount) {
        this.semestersCount = semestersCount;
    }

    public int getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(int sheetNumber) {
        currentSheet = excelBook.getSheetAt(sheetNumber);
        if (currentSheet != null) {
            this.sheetNumber = sheetNumber;
        } else {
            currentSheet = excelBook.getSheetAt(this.sheetNumber);
        }
    }
}
