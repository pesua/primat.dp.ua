package ua.dp.primat.curriculum.view;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.StudentGroupRepository;
import ua.dp.primat.curriculum.data.WorkloadEntry;
import ua.dp.primat.curriculum.data.WorkloadEntryRepository;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    private StudentGroup chosenGroup;
    private Long chosenSemester;
    private final ListView<WorkloadEntry> disciplinesView;
    private static final int semesterCount = 8;

    public HomePage() {
        
        final List<StudentGroup> groups = studentGroupRepository.getGroups();

        if (groups.isEmpty()) {
            throw new RestartResponseAtInterceptPageException(NoCurriculumsPage.class);
        }
             
        chosenGroup = groups.get(0);
        chosenSemester = Long.valueOf(1);
        
        Form form = new Form("form");
        add(form);
        DropDownChoice<StudentGroup> groupChoise = new CurriculumChoise<StudentGroup>("group",
                new PropertyModel<StudentGroup>(this, "chosenGroup"),
                new LoadableDetachableModelImpl(groups));
        form.add(groupChoise);

        DropDownChoice<Long> semesterChoise = new CurriculumChoise<Long>("semester",
                new PropertyModel<Long>(this, "chosenSemester"),
                new LoadableDetachableModel<List<Long>>(){

            @Override
            protected List<Long> load() {
                List<Long> l = new ArrayList<Long>();
                for (int i = 1; i <= semesterCount; i++) {
                    l.add(Long.valueOf(i));
                }
                return l;
            }
        });

        form.add(semesterChoise);

        List<WorkloadEntry> workloadEntries = workloadEntryRepository.getWorkloadEntries(chosenGroup, chosenSemester);
        disciplinesView = new WorkloadsListView("disciplineRow", workloadEntries);

        add(disciplinesView);
    }

    private static class WorkloadsListView extends ListView<WorkloadEntry> {

        public WorkloadsListView(String string, List<? extends WorkloadEntry> list) {
            super(string, list);
        }

        @Override
        protected void populateItem(ListItem<WorkloadEntry> li) {
            final WorkloadEntry entry = li.getModelObject();
            li.add(new Label("disciplineName", entry.getWorkload().getDiscipline().getName()));
            li.add(new Label("cathedra", entry.getWorkload().getDiscipline().getCathedra().getName()));
            li.add(new Label("lection", entry.getLectionCount().toString()));
            li.add(new Label("practice", entry.getPracticeCount().toString()));
            li.add(new Label("labs", entry.getLabCount().toString()));
            li.add(new Label("selfwork", entry.getIndCount().toString()));
            li.add(new Label("course", entry.getCourceWork().toString()));
            li.add(new Label("finalcontrol", entry.getFinalControl().toString()));
        }
    }

    @SpringBean
    private StudentGroupRepository studentGroupRepository;

    @SpringBean
    private WorkloadEntryRepository workloadEntryRepository;

    static class LoadableDetachableModelImpl extends LoadableDetachableModel<List<StudentGroup>> {

        private final List<StudentGroup> groups;

        public LoadableDetachableModelImpl(List<StudentGroup> groups) {
            this.groups = groups;
        }

        @Override
        protected List<StudentGroup> load() {
            return groups;
        }
    }

    private class CurriculumChoise<T> extends DropDownChoice<T> {

        public CurriculumChoise(String id, IModel<T> model, IModel<? extends List<? extends T>> choices) {
            super(id, model, choices);
        }

        @Override
        protected void onSelectionChanged(T newSelection) {
            disciplinesView.setList(workloadEntryRepository.getWorkloadEntries(chosenGroup, chosenSemester));
            super.onSelectionChanged(newSelection);
        }

        @Override
        protected boolean wantOnSelectionChangedNotifications() {
            return true;
        }
    }
}
