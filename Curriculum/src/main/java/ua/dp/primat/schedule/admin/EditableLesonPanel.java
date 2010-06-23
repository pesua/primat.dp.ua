package ua.dp.primat.schedule.admin;

import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.Room;
import ua.dp.primat.schedule.services.EditScheduleService;
import ua.dp.primat.schedule.services.EditableLesson;

/**
 *
 * @author EniSh
 */
public class EditableLesonPanel extends Panel {
    public EditableLesonPanel(String id, EditableLesson lesson) {
        super (id);
        List<Room> rooms = editScheduleService.getRooms();
        List<Discipline> disciplines = editScheduleService.getDisciplines();
        List<Lecturer> lecturers = editScheduleService.getLecturers();

        add(new DropDownChoice<Discipline>("discipline", new PropertyModel<Discipline>(lesson, "discipline"), disciplines));
        add(new DropDownChoice<Room>("room", new PropertyModel<Room>(lesson, "room"), rooms));
        add(new DropDownChoice<Lecturer>("lecturer", new PropertyModel<Lecturer>(lesson, "lecturer"), lecturers));
        add(new DropDownChoice<Lecturer>("asistant", new PropertyModel<Lecturer>(lesson, "asistant"), lecturers));
    }

    @SpringBean
    private EditScheduleService editScheduleService;
}
