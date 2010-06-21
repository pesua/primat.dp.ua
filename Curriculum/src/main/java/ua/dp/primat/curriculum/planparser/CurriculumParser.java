package ua.dp.primat.curriculum.planparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import ua.dp.primat.domain.workload.LoadCategory;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.workload.WorkloadType;

/**
 *
 * @author fdevelop
 *
 */
public final class CurriculumParser {

    //Workload Type Indexes
    public static final int WTID_HUMANITIES = 1;
    public static final int WTID_NATURALSCIENCE = 2;
    public static final int WTID_PROFPRACT = 3;
    public static final int WTID_PROFPRACTSTUDENT = 4;
    public static final int WTID_PROFPRACTUNIVER = 5;

    /* PRIVATE VARIABLES */
    private String diffSetOff;
    private String disciplineCategorySelective;
    private String disciplineCategoryAlternativeWar;

    private WorkloadType currentWorkloadType;
    private LoadCategory currentLoadCategory;

    //pre-params
    private StudentGroup group;
    private int sheetNumber;
    private int itemStart;
    private int itemEnd;
    private int semestersCount;
    private String fileName;

    /**
     * Constructor, that takes parser options.
     * @param group StudentGroup Entity that all items from this plan will be assigned to.
     * @param sheetNum Number of Excel sheet with the curriculum (starts from 0)
     * @param itemStart Number of row, that will be first to parse.
     * Generally, it is the first row after the curriculum header (starts from 0)
     * @param itemEnd Number of row, that will be last to parse (starts from 0)
     * @param semCount Count of semesters in the curriculum
     * @param fileName Excel file name with input curriculum
     */
    public CurriculumParser(StudentGroup group, int sheetNum,
            int itemStart, int itemEnd, int semCount, String fileName) {
        this.group = group;
        this.itemStart = itemStart;
        this.itemEnd = itemEnd;
        this.sheetNumber = sheetNum;
        this.semestersCount = semCount;
        this.fileName = fileName;

        languageLoad("uk");
    }

    /**
     * Launch the Parse procedure for configured (by constructor) CurriculumParser.
     * @return list of CurriculumXLSRow, that contains parsed values
     *
     * @throws IOException if there is a problem on accessing to the file, specififed by fileName in constructor
     */
    public List<CurriculumXLSRow> parse() throws IOException {
        final Workbook excelBook = new HSSFWorkbook(new FileInputStream(new File(fileName)));
        final Sheet currentSheet = excelBook.getSheetAt(sheetNumber);

        if ((itemStart < 0) || (itemEnd > currentSheet.getPhysicalNumberOfRows()-1)) {
            throw new IndexOutOfBoundsException();
        }

        //assign default values for type and category of discipline
        currentWorkloadType = WorkloadType.wtProfPract;
        currentLoadCategory = LoadCategory.Normative;

        final List<CurriculumXLSRow> entries = new ArrayList<CurriculumXLSRow>();
        //start reading excel rows
        for (int r = itemStart; r <= itemEnd; r++) {
            final Row row = currentSheet.getRow(r);

            //could not parse item
            if ((row == null) 
                || (row.getPhysicalNumberOfCells() == 0)
                || (row.getCell(0).toString().length() == 0)) {
                continue;
            }

            //if this item is not a Database entry, it might be the option (WorkloadType, LoadCategory)
            if ((row.getCell(1) == null) || (row.getCell(1).toString().length() == 0)) {
                String cellText = row.getCell(0).getStringCellValue().trim();
                changeEntriesCategoryOrType(cellText);
            } else {
                entries.add(new CurriculumXLSRow(group, row,
                        currentWorkloadType, currentLoadCategory,
                        semestersCount, diffSetOff));
            }
        }
        
        //get the result
        return entries;
    }

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

    public int getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    /**
     * Load language resources from .properties file.
     * @param lang  language identification (like "en", "uk" etc)
     */
    private void languageLoad(String lang) {
        final ResourceBundle parserConstants = ResourceBundle.getBundle("parser", new Locale(lang));
        disciplineCategorySelective = parserConstants.getString("category.selective");
        disciplineCategoryAlternativeWar = parserConstants.getString("category.alternativewar");
        diffSetOff = parserConstants.getString("differentialsetoff");
    }

    /**
     * Set the value of currentLoadCategory or currentWorkloadType, parsed from cell value.
     * @param cellText  cell string value
     */
    private void changeEntriesCategoryOrType(String cellText) {
        int dtype;
        if ( cellText.indexOf('.') > -1 ) {
            try {
                dtype = Integer.parseInt( cellText.substring(0, cellText.indexOf('.')) );
            }
            catch (NumberFormatException nfe) {
                dtype = 0;
            }
            currentLoadCategory = LoadCategory.Normative;
            switch (dtype) {
                case WTID_HUMANITIES:currentWorkloadType = WorkloadType.wtHumanities;
                    break;
                case WTID_NATURALSCIENCE:currentWorkloadType = WorkloadType.wtNaturalScience;
                    break;
                case WTID_PROFPRACT:currentWorkloadType = WorkloadType.wtProfPract;
                    break;
                case WTID_PROFPRACTSTUDENT:currentWorkloadType = WorkloadType.wtProfPractStudent;
                    break;
                case WTID_PROFPRACTUNIVER:currentWorkloadType = WorkloadType.wtProfPractUniver;
                    break;
                default: currentWorkloadType = WorkloadType.wtProfPract;
            }
        } else {
            if (cellText.contains(disciplineCategoryAlternativeWar)) {
                currentLoadCategory = LoadCategory.AlternativeForWar;
            } else if (cellText.contains(disciplineCategorySelective)) {
                currentLoadCategory = LoadCategory.Selective;
            } else {
                currentLoadCategory = LoadCategory.Normative;
            }
        }
    }
}