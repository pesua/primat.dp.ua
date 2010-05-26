package ua.dp.primat.curriculum.view;

import java.io.File;
import java.util.List;
import org.apache.wicket.Application;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.markup.html.form.upload.*;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.*;
import org.apache.wicket.validation.validator.RangeValidator;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.StudentGroupRepository;
import ua.dp.primat.curriculum.data.WorkloadRepository;
import ua.dp.primat.curriculum.data.Workload;
import ua.dp.primat.curriculum.planparser.CurriculumParser;
import ua.dp.primat.curriculum.planparser.CurriculumXLSRow;

public class EditPage extends WebPage {

    private static final long serialVersionUID = 2L;

    public EditPage() {
        Form form = new FileUploadForm("formUploadXLS");
        add(form);
    }

    /**
     * Form for uploads.
     */
    private class FileUploadForm extends Form<Void>
    {
        private FileUploadField fileUploadField;
        private TextField<Integer> textParseSheet;
        private TextField<Integer> textParseStart;
        private TextField<Integer> textParseEnd;
        private TextField<Integer> textParseSemester;
        private TextField<String> textGroupSpec;
        private TextField<Integer> textGroupYear;
        private TextField<Integer> textGroupNumber;

        /**
         * Construct.
         */
        public FileUploadForm(String name)
        {
            super(name);
            // set this form to multipart mode (allways needed for uploads!)
            setMultiPart(true);
            // Add one file input field
            add(fileUploadField = new FileUploadField("fileInput"));
            add(textParseSheet = new TextField<Integer>("parseSheet", new Model<Integer>(), Integer.class));
            add(textParseStart = new TextField<Integer>("parseStart", new Model<Integer>(), Integer.class));
            add(textParseEnd = new TextField<Integer>("parseEnd", new Model<Integer>(), Integer.class));
            add(textParseSemester = new TextField<Integer>("parseSemester", new Model<Integer>(), Integer.class));
            add(textGroupSpec = new TextField<String>("groupSpec", new Model<String>(), String.class));
            add(textGroupYear = new TextField<Integer>("groupYear", new Model<Integer>(), Integer.class));
            add(textGroupNumber = new TextField<Integer>("groupNumber", new Model<Integer>(), Integer.class));
            textParseSheet.setRequired(true);
            textParseSheet.add(new RangeValidator<Integer>(0, Integer.MAX_VALUE));
            textParseStart.setRequired(true);
            textParseStart.add(new RangeValidator<Integer>(0, Integer.MAX_VALUE));
            textParseEnd.setRequired(true);
            textParseEnd.add(new RangeValidator<Integer>(0, Integer.MAX_VALUE));
            textParseSemester.setRequired(true);
            textParseSemester.add(new RangeValidator<Integer>(0, Integer.MAX_VALUE));
            textGroupSpec.setRequired(true);
            textGroupYear.setRequired(true);
            textGroupYear.add(new RangeValidator<Integer>(1910, 2110));
            textGroupNumber.setRequired(true);
            textGroupNumber.add(new RangeValidator<Integer>(1, 20));
            // Set maximum size to 10M
            setMaxSize(Bytes.megabytes(10));
        }

        private String makeUploadedFile(FileUpload upload) {
            if (upload != null)
            {
                // Create a new file
                File newFile = new File(getUploadFolder(), upload.getClientFileName());

                // Check new file, delete if it allready existed
                checkFileExists(newFile);
                try
                {
                    // Save to new file
                    newFile.createNewFile();
                    upload.writeTo(newFile);
                    //EditPage.this.info("saved file: " + upload.getClientFileName() + "\nOriginal saved in: " + newFile.getAbsolutePath());
                    return newFile.getAbsolutePath();
                }
                catch (Exception e)
                {
                    throw new IllegalStateException("Unable to write file");
                }
            } else {
                return null;
            }
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        protected void onSubmit()
        {
            Integer parseSheet = textParseSheet.getConvertedInput();
            Integer parseStart = textParseStart.getConvertedInput();
            Integer parseEnd = textParseEnd.getConvertedInput();
            Integer parseSemesters = textParseSemester.getConvertedInput();
            String groupSpec = textGroupSpec.getConvertedInput();
            Integer groupYear = textGroupYear.getConvertedInput();
            Integer groupNumber = textGroupNumber.getConvertedInput();
            FileUpload upload = fileUploadField.getFileUpload();

            //upload and create file on server
            String uploadedFileName = makeUploadedFile(upload);
            /*EditPage.this.info("uploadedFileName: " + uploadedFileName + "\n"
                    + " parseSheet: " + parseSheet
                    + " parseStart: " + parseStart
                    + " parseEnd: " + parseEnd
                    + " parseSemesters: " + parseSemesters );*/

            parseGroup = new StudentGroup(groupSpec, new Long(groupNumber), new Long(groupYear));
            //parser launch
            CurriculumParser cParser = new CurriculumParser(parseGroup,
                    parseSheet, parseStart, parseEnd, parseSemesters,
                    uploadedFileName);
            List<CurriculumXLSRow> listParsed = cParser.parse();

            //commit info to the database
            for (int i=0;i<listParsed.size();i++) {
                Workload workload = listParsed.get(i).getWorkload();
                workloadRepository.store(workload);
            }
        }
    }

    /**
     * Check whether the file allready exists, and if so, try to delete it.
     *
     * @param newFile
     *            the file to check
     */
    private void checkFileExists(File newFile)
    {
        if (newFile.exists())
        {
            // Try to delete the file
            if (!Files.remove(newFile))
            {
                throw new IllegalStateException("Unable to overwrite " + newFile.getAbsolutePath());
            }
        }
    }

    private Folder getUploadFolder()
    {
        return ((WicketApplication)Application.get()).getUploadFolder();
    }

    @SpringBean
    StudentGroupRepository studentGroupRepository;

    @SpringBean
    WorkloadRepository workloadRepository;

    private StudentGroup parseGroup;
}
