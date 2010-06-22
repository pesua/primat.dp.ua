package ua.dp.primat.curriculum.planparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

            if ((row.getCell(1) == null) || (row.getCell(1).toString().length() == 0)) {
                //not a curriculum row, but information item
                changeEntriesCategoryOrType(row.getCell(0).getStringCellValue().trim());
            } else {
                entries.add(new CurriculumXLSRow(group, row,
                        currentWorkloadType, currentLoadCategory,
                        semestersCount, diffSetOff));
            }
        }
        
        return entries;
    }

    /**
     * Load language resources from .properties file.
     * @param lang - language identification (like "en", "uk" etc)
     */
    private void languageLoad(String lang) {
        final ResourceBundle parserConstants = ResourceBundle.getBundle("parser", new Locale(lang));
        disciplineCategorySelective = parserConstants.getString("category.selective");
        disciplineCategoryAlternativeWar = parserConstants.getString("category.alternativewar");
        diffSetOff = parserConstants.getString("differentialsetoff");
    }

    /**
     * Set the value of currentLoadCategory or currentWorkloadType, parsed from cell value.
     * @param cellText - cell string value
     */
    private void changeEntriesCategoryOrType(String cellText) {
        if ( cellText.contains(".") ) {
            int dtype = getWorkloadTypeInfoNumber(cellText);
            currentLoadCategory = LoadCategory.Normative;
            currentWorkloadType = WorkloadType.fromNumber(dtype);
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

    /**
     * Returns the number from the string like '1. Param-pam-pam'
     * @param text - input string
     * @return
     */
    private int getWorkloadTypeInfoNumber(String text) {
        try {
            return Integer.parseInt( text.substring(0, text.indexOf('.')) );
        }
        catch (NumberFormatException nfe) {
            return 0;
        }
    }

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
}