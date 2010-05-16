package ua.dp.primat.curriculum;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
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
    public HomePage(final PageParameters parameters) {
        StudentGroup gr = new StudentGroup();
        gr.setCode("PZ");
        // Add the simplest type of label
        add(new Label("message", "Curriclums will be here"));
        GroupChoser groupChoser = new GroupChoser("form");

        add(groupChoser);
        groupChoser.add(new Label("group", "kjkjk"));//new PropertyModel(gr, "code")
        
        // TODO Add your page's components here
    }

    public final class GroupChoser extends Form {
        public GroupChoser(final String componentName){
            super(componentName);
        }

        @Override
        protected void onSubmit() {
            super.onSubmit();
        }


    }
}
