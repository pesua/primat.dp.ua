package ua.dp.primat.curriculum;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import ua.dp.primat.curriculum.data.DataUtils;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.WorkloadEntry;

/**
 * Homepage
 */
public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    private StudentGroup choosenGroup;
    private Long choosenSemester;
    private final ListView<WorkloadEntry> disciplinesViev;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public HomePage() {//final PageParameters parameters
        
        final List groups = DataUtils.getGroups();
     
        if (groups.size() < 1) {
            throw new IllegalArgumentException("Sorry, but no groups in the database");
        }
        
        //when user visit page firstly, he haven't made choise
        //and we heve to init default choise for him
        if(choosenGroup == null) {
            choosenGroup = (StudentGroup) groups.get(0);
        }
        if(choosenSemester == null) {
            choosenSemester = new Long(1); 
        }

        //get necessary to us workloads
        List<WorkloadEntry> workloadEntries = DataUtils.getWorkloadEntries(choosenGroup, choosenSemester);

        Form form = new ChooseGroupForm("form");
        add(form);
        DropDownChoice groupChoise = new DropDownChoice("group",
                new PropertyModel(this, "choosenGroup"),
                new LoadableDetachableModel() {

            @Override
            protected Object load() {
                return groups;
            }
        });
        form.add(groupChoise);

        DropDownChoice semesterChoise = new DropDownChoice("semester",
                new PropertyModel(this, "choosenSemester"),
                new LoadableDetachableModel() {

            @Override
            protected Object load() {
                List l = new ArrayList();
                for (int i = 1; i <= DataUtils.getSemesterCount(choosenGroup); i++) {
                    l.add(new Long(i));
                }
                return l;
            }
        });
        form.add(semesterChoise);

        disciplinesViev = new ListViewImpl("disciplineRow", workloadEntries);

        add(disciplinesViev);

    }

    private static class ListViewImpl extends ListView<WorkloadEntry> {

        public ListViewImpl(String string, List<? extends WorkloadEntry> list) {
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
            List<WorkloadEntry> workloadEntries = DataUtils.getWorkloadEntries(choosenGroup, choosenSemester);
            disciplinesViev.setList(workloadEntries);
            super.onSubmit();
        }
    }
}
