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
private static final long serialVersionUID = 1L;

    public EditableLesonPanel(String id, EditableLesson lesson) {
        super (id);
        final List<Room> rooms = editScheduleService.getRooms();
        final List<Discipline> disciplines = editScheduleService.getDisciplines();
        final List<Lecturer> lecturers = editScheduleService.getLecturers();

        add(new DropDownChoice<Discipline>("disciplineDDC", new PropertyModel<Discipline>(lesson, "discipline"), disciplines));
        add(new DropDownChoice<Room>("roomDDC", new PropertyModel<Room>(lesson, "room"), rooms));
        add(new DropDownChoice<Lecturer>("lecturerDDC", new PropertyModel<Lecturer>(lesson, "lecturer"), lecturers));
        add(new DropDownChoice<Lecturer>("asistantDDC", new PropertyModel<Lecturer>(lesson, "asistant"), lecturers));
    }

    @SpringBean
    private EditScheduleService editScheduleService;
}
