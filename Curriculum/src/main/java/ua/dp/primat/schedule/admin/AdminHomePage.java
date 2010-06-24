package ua.dp.primat.schedule.admin;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.repositories.StudentGroupRepository;

/**
 *
 * @author EniSh
 */
public final class AdminHomePage extends WebPage {

    public AdminHomePage() {
        super();

        add(new EditScheduleForm("editScheduleForm"));
    }

    private class EditScheduleForm extends Form {

        public EditScheduleForm(String cname) {
            super(cname);
            final int semesterCount = 8;
            List<StudentGroup> groups = studentGroupRepository.getGroups();
            List<Long> semesters = new ArrayList<Long>();
            for (int i = 0; i < semesterCount; i++) {
                semesters.add(Long.valueOf(i));
            }
            add(new DropDownChoice<StudentGroup>("group", new PropertyModel(this, "group"), groups));
            add(new DropDownChoice<Long>("semester", new PropertyModel<Long>(this, "semester"), semesters));
        }

        @Override
        protected void onSubmit() {
            setResponsePage(new EditSchedulePage(group, semester));
        }
        private Long semester = Long.valueOf(1);
        private StudentGroup group;
        @SpringBean
        private StudentGroupRepository studentGroupRepository;
    }
}
