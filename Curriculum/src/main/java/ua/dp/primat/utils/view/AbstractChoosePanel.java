package ua.dp.primat.utils.view;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.repositories.StudentGroupRepository;
import ua.dp.primat.curriculum.view.NoCurriculumsPage;

/**
 * Wicket panel for the Group and Semester choosing.
 * @author fdevelop
 */
public abstract class AbstractChoosePanel extends Panel {

    /**
     * Constructor, that creates combo for groups and for semesters.
     * @param id
     */
    public AbstractChoosePanel(String id) {
        super(id);

        final List<StudentGroup> groups = studentGroupRepository.getGroups();
        if (groups.isEmpty()) {
            throw new RestartResponseAtInterceptPageException(NoCurriculumsPage.class);
        }

        //set the default values
        studentGroup = groups.get(0);
        semester = Long.valueOf(1);
        executeAction(studentGroup, semester);

        final DropDownChoice<StudentGroup> groupChoice = new DropDownChoice<StudentGroup>("group",
                new PropertyModel<StudentGroup>(this, "studentGroup"),
                new GroupsLoadableDetachableModel(groups)) {

            @Override
            protected void onSelectionChanged(StudentGroup newSelection) {
                studentGroup = newSelection;

                executeAction(studentGroup, semester);
                super.onSelectionChanged(studentGroup);
            }

            @Override
            protected boolean wantOnSelectionChangedNotifications() {
                return true;
            }

        };
        add(groupChoice);

        //semester combo
        final String sSemester = new String("semester");
        final DropDownChoice<Long> semesterChoise = new DropDownChoice<Long>(sSemester,
                new PropertyModel<Long>(this, sSemester),
                new LoadableDetachableModel<List<Long>>(){

            @Override
            protected List<Long> load() {
                final List<Long> l = new ArrayList<Long>();
                for (int i = 1; i <= SEMESTERCOUNT; i++) {
                    l.add(Long.valueOf(i));
                }
                return l;
            }

        }) {
            @Override
            protected void onSelectionChanged(Long newSelection) {
                semester = newSelection;

                executeAction(studentGroup, semester);
                super.onSelectionChanged(semester);
            }

            @Override
            protected boolean wantOnSelectionChangedNotifications() {
                return true;
            }
        };
        add(semesterChoise);
    }

    /**
     * Method, which invokes on changing selection of one of the comboboxes.
     * @param studentGroup - choosed student group
     * @param semester - choosed semester
     */
    protected abstract void executeAction(StudentGroup studentGroup, Long semester);

    private StudentGroup studentGroup;
    private Long semester;

    @SpringBean
    private StudentGroupRepository studentGroupRepository;

    private static final int SEMESTERCOUNT = 8;

}
