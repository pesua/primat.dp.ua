package ua.dp.primat.curriculum.view;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.workload.WorkloadEntry;
import ua.dp.primat.repositories.WorkloadEntryRepository;
import ua.dp.primat.utils.view.ChoosePanel;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;
    @SpringBean
    private WorkloadEntryRepository workloadEntryRepository;
    private ListView<WorkloadEntry> workloadsView;
    private List<WorkloadEntry> workloadEntries;

    public HomePage() {

        final ChoosePanel choosePanel = new ChoosePanel("choosePanel") {

            @Override
            protected void executeAction(StudentGroup studentGroup, Long semester) {
                workloadEntries = workloadEntryRepository.getWorkloadEntries(studentGroup, semester);
                if (workloadsView != null) {
                    workloadsView.setList(workloadEntries);
                }
            }
        };
        add(choosePanel);

        workloadsView = new WorkloadsListView("disciplineRow", workloadEntries);
        add(workloadsView);
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
            li.add(new Image("course") {

                @Override
                public boolean isVisible() {
                    return entry.getCourceWork();
                }
            });
            li.add(new Label("finalcontrol", entry.getFinalControl().toString()));
        }
    }
}
