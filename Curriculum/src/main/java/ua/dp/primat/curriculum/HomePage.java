package ua.dp.primat.curriculum;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import ua.dp.primat.curriculum.data.StudentGroup;

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
    StudentGroup choosenGroup;
    
    
    public HomePage(final PageParameters parameters) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("curriculum");
        EntityManager em = emFactory.createEntityManager();
        //logic
        em.getTransaction().begin();

        final List groups = em.createQuery("from StudentGroup").getResultList();
        if(choosenGroup == null)
            choosenGroup = (StudentGroup) groups.get(0);

        em.close();
        Form form = new Form("form");
        add(form);


        DropDownChoice ddc = new DropDownChoice("group",
                new PropertyModel(this, "choosenGroup"),
                new LoadableDetachableModel() {

            @Override
            protected Object load() {
                return groups;
            }
        });
        form.add(ddc);
    }
}
