package dp.ua.fpm;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

public class Page1 extends WebPage {

    public Page1() {
        Person employee = new Person(new Long(7), "Pupkin", null);
        //NavomaticBorder border = new NavomaticBorder("navomaticBorder");
        //add(border);

        Form form = new Form("form");
        add(form);
        form.add(new Label("name", new PropertyModel(employee, "name")));

        DropDownChoice ddc =
                new DropDownChoice("managedBy",
                new PropertyModel(employee, "managedBy"),
                new LoadableDetachableModel() {

                    @Override
                    protected Object load() {
                        return Person.getManagers();
                    }
                });
        form.add(ddc);
    }
}
