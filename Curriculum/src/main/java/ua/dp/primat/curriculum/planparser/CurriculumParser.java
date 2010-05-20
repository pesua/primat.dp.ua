package ua.dp.primat.curriculum.planparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import ua.dp.primat.curriculum.data.LoadCategory;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.WorkloadType;

/**
 *
 * @author fdevelop
 *
 */
public final class CurriculumParser {

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

    private CurriculumXLSRow parseXLSEntry(Row row, WorkloadType workloadType, LoadCategory loadCategory) {
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
        Map<Integer, WorkHours> semesterWorkhours = new HashMap<Integer, WorkHours>();
        for (int sem=0; sem < semestersCount; sem++) {
            WorkHours semesterHoursInfo = new WorkHours();
            semesterHoursInfo.hLec = row.getCell(COL_HOURS_LECTURE+COL_HOUROFFSET*sem).getNumericCellValue();
            semesterHoursInfo.hPract = row.getCell(COL_HOURS_PRACTICE+COL_HOUROFFSET*sem).getNumericCellValue();
            semesterHoursInfo.hLab = row.getCell(COL_HOURS_LAB+COL_HOUROFFSET*sem).getNumericCellValue();
            semesterHoursInfo.hInd = row.getCell(COL_HOURS_INDIVIDUAL+COL_HOUROFFSET*sem).getNumericCellValue();
            semesterHoursInfo.hSam = row.getCell(COL_HOURS_SELFWORK+COL_HOUROFFSET*sem).getNumericCellValue();
            if (semesterHoursInfo.getSum() > 0)
                semesterWorkhours.put(sem+1, semesterHoursInfo);
        }

        //finally, create object
        return new CurriculumXLSRow(group, subjName, subjCath,
                workCtrlExams, workCtrlMark, workCtrlCourse,
                workIndSem, workIndForm, workIndWeek, semesterWorkhours,
                workloadType, loadCategory);
    }

    public List<CurriculumXLSRow> parse() {
        List<CurriculumXLSRow> entries = new ArrayList<CurriculumXLSRow>();

        //assign default values for type and category of discipline
        WorkloadType workloadType = WorkloadType.wtProfPract;
        LoadCategory loadCategory = LoadCategory.Normative;

        if ((itemStart < 0) || (itemEnd > currentSheet.getPhysicalNumberOfRows()-1)) {
            throw new IndexOutOfBoundsException();
        }

        for (int r = itemStart; r <= itemEnd; r++) {
            Row row = currentSheet.getRow(r);

            //could not parse item
            if ((row == null) ||
                (row.getPhysicalNumberOfCells() == 0) ||
                (row.getCell(0).toString().isEmpty())) {
                continue;
            }

            //if this item is not a Database entry, it might be the options
            if ((row.getCell(1) == null) || (row.getCell(1).toString().isEmpty())) {
                Cell cell = row.getCell(0);
                if ( cell.getStringCellValue().indexOf(". ") > -1 ) {
                    int dtype = Integer.parseInt( cell.getStringCellValue().substring(0, cell.getStringCellValue().indexOf(".")) );
                    switch (dtype) {
                        case 1:workloadType = WorkloadType.wtHumanities;
                        case 2:workloadType = WorkloadType.wtNaturalScience;
                        case 3:workloadType = WorkloadType.wtProfPract;
                        case 4:workloadType = WorkloadType.wtProfPractStudent;
                        case 5:workloadType = WorkloadType.wtProfPractUniver;
                    }
                    loadCategory = LoadCategory.Normative;
                }
                else {
                    String categoryName = cell.getStringCellValue();
                    if (categoryName.equalsIgnoreCase(DISCIPLINE_CAT_ALTERNATIVEWAR))
                        loadCategory = LoadCategory.AlternativeForWar;
                    else if (categoryName.equalsIgnoreCase(DISCIPLINE_CAT_SELECTIVE))
                        loadCategory = LoadCategory.Selective;
                    else
                        loadCategory = LoadCategory.Normative;
                }
            } else {
                entries.add(parseXLSEntry(row, workloadType, loadCategory));
            }
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
    //
    public static final int COL_HOUROFFSET = 6;

    /* VARIABLES */
    private Workbook excelBook;
    private Sheet currentSheet;

    //pre-params
    private StudentGroup group;
    private int sheetNumber;
    private int itemStart;
    private int itemEnd;
    private int semestersCount;

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