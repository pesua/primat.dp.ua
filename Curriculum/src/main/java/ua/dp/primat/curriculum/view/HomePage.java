package ua.dp.primat.curriculum.view;

import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.workload.Workload;
import ua.dp.primat.repositories.WorkloadRepository;
import ua.dp.primat.utils.view.AbstractChoosePanel;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;
    @SpringBean
    private WorkloadRepository workloadRepository;
    private ListView<Workload> workloadsView;
    private List<Workload> workloads;

    public HomePage() {
        super();

        final AbstractChoosePanel choosePanel = new ChoosePanel("choosePanel");
        add(choosePanel);

        workloadsView = new WorkloadsListView("disciplineRow", workloads);
        add(workloadsView);
    }

    private class ChoosePanel extends AbstractChoosePanel {

        ChoosePanel(String id) {
            super(id);
        }

        @Override
        protected void executeAction(StudentGroup studentGroup, Long semester) {
            workloads = workloadRepository.getWorkloadsByGroupAndSemester(studentGroup, semester);
            if (workloadsView != null) {
                workloadsView.setList(workloads);
            }
        }
        private static final long serialVersionUID = 2L;
    }

    private static class WorkloadsListView extends ListView<Workload> {

        public WorkloadsListView(String string, List<Workload> list) {
            super(string, list);
        }

        @Override
        protected void populateItem(ListItem<Workload> li) {
            workload = li.getModelObject();
            li.add(new Label("disciplineName", workload.getDiscipline().getName()));
            li.add(new Label("cathedra", workload.getDiscipline().getCathedra().getName()));
            li.add(new Label("lection", workload.getLectionHours().toString()));
            li.add(new Label("practice", workload.getPracticeHours().toString()));
            li.add(new Label("labs", workload.getLaboratoryHours().toString()));
            li.add(new Label("selfwork", workload.getSelfworkHours().toString()));
            li.add(new ImageCourse("course"));
            li.add(new Label("finalcontrol", workload.getFinalControlType().toString()));
        }
        private static Workload workload;
        private class ImageCourse extends Image {

            ImageCourse(String id) {
                super(id);
            }

            @Override
            public boolean isVisible() {
                return workload.getCourseWork();
            }
            private static final long serialVersionUID = 2L;
        }
        private static final long serialVersionUID = 2L;
    }
}
