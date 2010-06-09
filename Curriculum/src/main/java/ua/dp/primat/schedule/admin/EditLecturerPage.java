/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.primat.schedule.admin;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.ComponentPropertyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.schedule.data.Lecturer;
import ua.dp.primat.schedule.data.LecturerRepositoryImpl;
import ua.dp.primat.schedule.data.LecturerType;

/**
 *
 * @author Administrator
 */
public final class EditLecturerPage extends WebPage {

    public EditLecturerPage() {
        this(new Lecturer());
    }

    public EditLecturerPage(Lecturer lecturer) {
        this.lecturer = lecturer;
        add(new EditLecturerForm("lecturer"));
    }

    private class EditLecturerForm extends Form<Lecturer> {

        public EditLecturerForm(String cname) {
            super(cname, new CompoundPropertyModel<Lecturer>(lecturer));
            add(new TextField("name"));
            List<Lecturer> lecturers = lecturerRepository.getAllLecturers();
            add(new DropDownChoice("cathedra", lecturers));
            List<LecturerType> lecturerTypes = new ArrayList<LecturerType>();
            lecturerTypes.add(LecturerType.ASSIATANT);
            lecturerTypes.add(LecturerType.DOCENT);
            lecturerTypes.add(LecturerType.PROFESSOR);
            lecturerTypes.add(LecturerType.SENIORLECTURER);
            add(new DropDownChoice("lecturerType", lecturerTypes));
        }

        @Override
        protected void onSubmit() {
            lecturerRepository.store(lecturer);
            setResponsePage(ManageLecturersPage.class);
        }
    }
    private Lecturer lecturer;
    @SpringBean
    private LecturerRepositoryImpl lecturerRepository;
}

