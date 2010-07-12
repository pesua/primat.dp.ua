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

    private static class EditScheduleForm extends Form {

        public EditScheduleForm(String cname) {
            super(cname);
            final int semesterCount = 8;
            final List<StudentGroup> groups = studentGroupRepository.getGroups();
            final List<Long> semesters = new ArrayList<Long>();
            final String sGroup = "group";
            final String sSemester = "semester";
            for (int i = 1; i <= semesterCount; i++) {
                semesters.add(Long.valueOf(i));
            }
            add(new DropDownChoice<StudentGroup>(sGroup, new PropertyModel(this, sGroup), groups));
            add(new DropDownChoice<Long>(sSemester, new PropertyModel<Long>(this, sSemester), semesters));
        }

        @Override
        protected void onSubmit() {
            setResponsePage(new EditSchedulePage(group, semester));
        }
        private Long semester = Long.valueOf(1);
        private StudentGroup group;
        @SpringBean
        private StudentGroupRepository studentGroupRepository;
        private static final long serialVersionUID = 1L;
    }
    private static final long serialVersionUID = 1L;
}
