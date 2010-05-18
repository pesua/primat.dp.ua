package ua.dp.primat.curriculum;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.Workload;
import ua.dp.primat.curriculum.data.WorkloadEntry;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    private StudentGroup choosenGroup;
    private Long choosenSemester;
    private ListView<WorkloadEntry> disciplinesView;
    
    public HomePage(final PageParameters parameters) {
        //load data from database
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("curriculum");
        EntityManager em = emFactory.createEntityManager();
       
        em.getTransaction().begin();

        final List groups = em.createQuery("from StudentGroup").getResultList();
     
        if (groups.size() < 1)
            groups.add(new StudentGroup("PZ", new Long(2008), new Long(1)));

        if(choosenGroup == null)
            choosenGroup = (StudentGroup) groups.get(0);

        if(choosenSemester == null)
            choosenSemester = new Long(1);

        Form form = new Form("form");
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
                for (int i = 1; i <= 8; i++)
                    l.add(new Long(i));
                return l;
            }
        });
        form.add(semesterChoise);

        //TODO must be refactored

        List<WorkloadEntry> workloadEntries = em.createQuery("from WorkloadEntry where semesterNumber = " + choosenSemester).getResultList();

        em.close();
        
        add(disciplinesView = new ListView<WorkloadEntry>("disciplineRow", workloadEntries) {

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
        });

    }
}
