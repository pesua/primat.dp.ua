package ua.dp.primat.schedule.admin.groupmanagement;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator.LengthBetweenValidator;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.repositories.StudentGroupRepository;

/**
 *
 * @author EniSh
 */
public final class EditGroupPage extends WebPage {

    public EditGroupPage() {
        this(new StudentGroup());
    }

    public EditGroupPage(StudentGroup group) {
        super();
        add(new EditGroupForm("form", group));
    }

    private class EditGroupForm extends Form<StudentGroup> {

        public EditGroupForm(String id, StudentGroup group) {
            super(id, new CompoundPropertyModel<StudentGroup>(group));

            add(new FeedbackPanel("feedback"));

            final RequiredTextField<String> speciality = new RequiredTextField<String>("code");
            speciality.add(new LengthBetweenValidator(2, 2));
            add(speciality);

            final RequiredTextField<Long> year = new RequiredTextField<Long>("year");
            year.add(new RangeValidator<Long>(MINYEAR, MAXYEAR));
            add(year);

            final RequiredTextField<Long> number = new RequiredTextField<Long>("number");
            number.add(new RangeValidator<Long>(1L, MAXNUMBER));
            add(number);
        }

        @Override
        protected void onSubmit() {
            groupRepository.store(getModelObject());
            setResponsePage(ManageGroupsPage.class);
        }
        private static final long serialVersionUID = 2L;
    }
    @SpringBean
    private StudentGroupRepository groupRepository;
    private static final long serialVersionUID = 1L;
    private static final long MINYEAR = 1950L;
    private static final long MAXYEAR = 2050L;
    private static final long MAXNUMBER = 10L;
}

