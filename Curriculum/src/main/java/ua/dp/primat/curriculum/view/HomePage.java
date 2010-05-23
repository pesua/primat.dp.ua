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
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.curriculum.data.DataUtils;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.WorkloadEntry;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    private StudentGroup chosenGroup;
    private Long chosenSemester;
    private final ListView<WorkloadEntry> disciplinesView;

    public HomePage() {
        
        final List<StudentGroup> groups = dataUtils.getGroups();

        if (groups.isEmpty()) {
            throw new RestartResponseAtInterceptPageException(NoCurriculumsPage.class);
        }
             
        chosenGroup = groups.get(0);
        chosenSemester = Long.valueOf(1);
        
        Form form = new ChooseGroupForm("form");
        add(form);
        DropDownChoice<StudentGroup> groupChoise = new DropDownChoice<StudentGroup>("group",
                new PropertyModel<StudentGroup>(this, "chosenGroup"),
                new LoadableDetachableModel<List<StudentGroup>>() {

            @Override
            protected List<StudentGroup> load() {
                return groups;
            }
        });
        form.add(groupChoise);

        DropDownChoice<Long> semesterChoise = new DropDownChoice<Long>("semester",
                new PropertyModel<Long>(this, "chosenSemester"),
                new LoadableDetachableModel<List<Long>>(){

            @Override
            protected List<Long> load() {
                List<Long> l = new ArrayList<Long>();
                for (int i = 1; i <= dataUtils.getSemesterCount(chosenGroup); i++) {
                    l.add(Long.valueOf(i));
                }
                return l;
            }
        });

        form.add(semesterChoise);

        List<WorkloadEntry> workloadEntries = dataUtils.getWorkloadEntries(chosenGroup, chosenSemester);
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

    private class ChooseGroupForm extends Form {

        public ChooseGroupForm(String id) {
            super(id);
        }

        @Override
        protected void onSubmit() {
            disciplinesView.setList(dataUtils.getWorkloadEntries(chosenGroup, chosenSemester));
            super.onSubmit();
        }
    }

    @SpringBean
    DataUtils dataUtils;
}
