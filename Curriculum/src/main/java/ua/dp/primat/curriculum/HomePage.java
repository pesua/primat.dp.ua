package ua.dp.primat.curriculum;

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
    public HomePage(final PageParameters parameters) {

        Form form = new Form("form");
        add(form);

        StudentGroup choosenGroup = new StudentGroup();
        final StudentGroup[] groups = new StudentGroup[2];
        groups[0] = new StudentGroup();
        groups[0].setCode("PZ");
        groups[0].setYear(new Long(2008));
        groups[0].setNumber(new Long(1));

        groups[1] = new StudentGroup();
        groups[1].setCode("PM");
        groups[1].setYear(new Long(2007));
        groups[1].setNumber(new Long(2));

        DropDownChoice ddc = new DropDownChoice("group",
                new PropertyModel(choosenGroup, "group"),
                new LoadableDetachableModel() {

            @Override
            protected Object load() {
                return groups;
            }
        });
    }
}
