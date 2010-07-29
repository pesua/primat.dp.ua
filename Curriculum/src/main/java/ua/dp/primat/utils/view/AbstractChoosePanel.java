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
import ua.dp.primat.schedule.services.Semester;
import ua.dp.primat.schedule.services.TimeService;

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
        semester = timeService.currentSemester();
        executeAction(studentGroup, timeService.semesterNumberForGroup(studentGroup, semester));

        final DropDownGroup groupChoice = new DropDownGroup("group",
                new PropertyModel<StudentGroup>(this, "studentGroup"),
                new GroupsLoadableDetachableModel(groups));
        add(groupChoice);

        //semester combo
        final String sSemester = "semester";
        final DropDownLong semesterChoise = new DropDownLong(sSemester,
                new PropertyModel<Semester>(this, sSemester),
                new LoadableDetachableModelList());
        add(semesterChoise);
    }

    /**
     * Method, which invokes on changing selection of one of the comboboxes.
     * @param studentGroup - choosed student group
     * @param semester - choosed semester
     */
    private class DropDownGroup extends DropDownChoice<StudentGroup> {

        DropDownGroup(String id,
                PropertyModel<StudentGroup> propmodel,
                GroupsLoadableDetachableModel groupmodel) {
            super(id, propmodel, groupmodel);
        }

        @Override
        protected void onSelectionChanged(StudentGroup newSelection) {
            studentGroup = newSelection;

            executeAction(studentGroup, timeService.semesterNumberForGroup(studentGroup, semester));
            super.onSelectionChanged(studentGroup);
        }

        @Override
        protected boolean wantOnSelectionChangedNotifications() {
            return true;
        }
        private static final long serialVersionUID = 1L;
    }

    private class DropDownLong extends DropDownChoice<Semester> {

        DropDownLong(String id, PropertyModel<Semester> propmodel, LoadableDetachableModel<List<Semester>> loadmodel) {
            super(id, propmodel, loadmodel);
        }

        @Override
        protected boolean wantOnSelectionChangedNotifications() {
            return true;
        }

        @Override
        protected void onSelectionChanged(Semester newSelection) {
            executeAction(studentGroup, timeService.semesterNumberForGroup(studentGroup, semester));
            super.onSelectionChanged(newSelection);
        }
        private static final long serialVersionUID = 1L;
    }

    private class LoadableDetachableModelList extends LoadableDetachableModel<List<Semester>> {

        @Override
        protected List<Semester> load() {
            return timeService.semestersForGroup(studentGroup);
        }
        private static final long serialVersionUID = 1L;
    }

    protected abstract void executeAction(StudentGroup studentGroup, Long semester);
    private StudentGroup studentGroup;
    private Semester semester;
    @SpringBean
    private StudentGroupRepository studentGroupRepository;
    @SpringBean
    private TimeService timeService;
}
