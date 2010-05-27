package ua.dp.primat.curriculum.view;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Application;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.markup.html.form.upload.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
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

    public EditPage() {
        final Form form = new FileUploadForm("formUploadXLS");
        add(form);

        //add feed back panel for system information output
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);
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
         * Constructor of form
         * @param name
         */
        public FileUploadForm(String name)
        {
            super(name);
            // set this form to multipart mode (allways needed for uploads!)
            setMultiPart(true);
            // Add file input field and other options fields
            fileUploadField = new FileUploadField("fileInput");
            add(fileUploadField);

            textParseSheet = new TextField<Integer>("parseSheet", new Model<Integer>(), Integer.class);
            textParseSheet.setRequired(true);
            textParseSheet.add(new RangeValidator<Integer>(0, Integer.MAX_VALUE));
            add(textParseSheet);

            textParseStart = new TextField<Integer>("parseStart", new Model<Integer>(), Integer.class);
            textParseStart.setRequired(true);
            textParseStart.add(new RangeValidator<Integer>(0, Integer.MAX_VALUE));
            add(textParseStart);

            textParseEnd = new TextField<Integer>("parseEnd", new Model<Integer>(), Integer.class);
            textParseEnd.setRequired(true);
            textParseEnd.add(new RangeValidator<Integer>(0, Integer.MAX_VALUE));
            add(textParseEnd);

            textParseSemester = new TextField<Integer>("parseSemester", new Model<Integer>(), Integer.class);
            textParseSemester.setRequired(true);
            textParseSemester.add(new RangeValidator<Integer>(0, Integer.MAX_VALUE));
            add(textParseSemester);

            textGroupSpec = new TextField<String>("groupSpec", new Model<String>(), String.class);
            textGroupSpec.setRequired(true);
            add(textGroupSpec);

            textGroupYear = new TextField<Integer>("groupYear", new Model<Integer>(), Integer.class);
            textGroupYear.setRequired(true);
            textGroupYear.add(new RangeValidator<Integer>(MIN_YEAR, MAX_YEAR));
            add(textGroupYear);
            
            textGroupNumber = new TextField<Integer>("groupNumber", new Model<Integer>(), Integer.class);
            textGroupNumber.setRequired(true);
            textGroupNumber.add(new RangeValidator<Integer>(1, MAX_GROUP_NUMBER));
            add(textGroupNumber);

            // Set maximum size to 10M
            setMaxSize(Bytes.megabytes(MAX_FILESIZE));
        }

        /**
         * Upload the file on server into the getUploadFolder() returned path.
         * @param upload
         * @return The absolute path to the uploaded file on server.
         */
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
         * Overriden method onSubmit for the FileUpload form.
         * It takes input fields arguments, uploads file, than parses it, stores
         * to the database and output the result.
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
            String uploadedFileName = "";
            try {
                uploadedFileName = makeUploadedFile(upload);
            }
            catch (IllegalStateException ise) {
                this.error(ise);
            }

            parseGroup = new StudentGroup(groupSpec, new Long(groupNumber), new Long(groupYear));
            //parser launch
            List<CurriculumXLSRow> listParsed = null;
            try {
                CurriculumParser cParser = new CurriculumParser(parseGroup,
                    parseSheet, parseStart, parseEnd, parseSemesters,
                    uploadedFileName);
                listParsed = cParser.parse();
            }
            catch (IOException ioe) {
                this.error(ioe);
            }

            //commit info to the database
            String parserLog = "";
            for (int i=0;i<listParsed.size();i++) {
                Workload workload = listParsed.get(i).getWorkload();
                workloadRepository.store(workload);
                parserLog = listParsed.get(i).toString();
                this.info(parserLog);
            }

            this.info(String.format("Curriculum in '%s' was successfully parsed\n into database (%d items).", uploadedFileName, listParsed.size()));
        }
    }

    /**
     * Check whether the file already exists, and if so, try to delete it.
     * @param newFile
     *          the file to check
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

    private StudentGroup parseGroup;

    @SpringBean
    private WorkloadRepository workloadRepository;

    private static final long serialVersionUID = 2L;
    private static final int MIN_YEAR = 1910;
    private static final int MAX_YEAR = 2110;
    private static final int MAX_GROUP_NUMBER = 20;
    private static final int MAX_FILESIZE = 10; //in megabytes
}
