package ua.dp.primat.curriculum;

import java.util.Arrays;
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
    StudentGroup choosenGroup = new StudentGroup("PS", new Long(2008), new Long(1));
    
    
    public HomePage(final PageParameters parameters) {

        Form form = new Form("form");
        add(form);

        final StudentGroup[] groups = new StudentGroup[2];
        groups[0] = new StudentGroup("PZ", new Long(2008), new Long(1));

        groups[1] = new StudentGroup("PM", new Long(2007), new Long(2));

        DropDownChoice ddc = new DropDownChoice("group",
                new PropertyModel(this, "choosenGroup"),
                new LoadableDetachableModel() {

            @Override
            protected Object load() {
                return Arrays.asList(groups);
            }
        });
        form.add(ddc);
    }
}
