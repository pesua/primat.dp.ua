/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.admin;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.StudentGroupRepository;

/**
 *
 * @author Administrator
 */
public final class AdminHomePage extends WebPage {
    public AdminHomePage() {
        super ();

        add(new EditScheduleForm("editScheduleForm"));
        add(new NewScheduleForm("newScheduleForm"));
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

    private class NewScheduleForm extends Form {

        public NewScheduleForm(String cname) {
            super(cname);
            final int semesterCount = 8;
            List<StudentGroup> groups = studentGroupRepository.getGroups();
            List<Long> semesters = new ArrayList<Long>();
            for (int i = 0; i < semesterCount; i++) {
                semesters.add(Long.valueOf(i));
            }
            add(new TextField("groupCode", new PropertyModel<String>(group, "code")));
            add(new TextField("groupYear", new PropertyModel<Long>(group, "year")));
            add(new TextField("groupNumber", new PropertyModel<Long>(group, "number")));
            add(new DropDownChoice<Long>("semester", new PropertyModel<Long>(this, "semester"), semesters));
        }

        @Override
        protected void onSubmit() {
            setResponsePage(new EditSchedulePage(group, semester));
            
        }

        private Long semester;
        private StudentGroup group = new StudentGroup();
        @SpringBean
        private StudentGroupRepository studentGroupRepository;
    }
}

