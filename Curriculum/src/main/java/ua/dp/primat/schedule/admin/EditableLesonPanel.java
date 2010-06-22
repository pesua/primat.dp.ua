/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.admin;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.repositories.LecturerRepository;
import ua.dp.primat.domain.Room;
import ua.dp.primat.repositories.DisciplineRepository;
import ua.dp.primat.repositories.RoomRepository;
import ua.dp.primat.schedule.services.EditableLesson;

/**
 *
 * @author EniSh
 */
public class EditableLesonPanel extends Panel {
    public EditableLesonPanel(String id, EditableLesson lesson) {
        super (id);
        List<Room> rooms = roomRepository.getRooms();
        List<Discipline> disciplines = disciplineRepository.getDisciplines();
        List<Lecturer> lecturers = lecturerRepository.getAllLecturers();

        add(new DropDownChoice<Discipline>("discipline", new PropertyModel<Discipline>(lesson, "discipline"), disciplines));
        add(new DropDownChoice<Room>("room", new PropertyModel<Room>(lesson, "room"), rooms));
        add(new DropDownChoice<Lecturer>("lecturer", new PropertyModel<Lecturer>(lesson, "lecturer"), lecturers));
        add(new DropDownChoice<Lecturer>("asistant", new PropertyModel<Lecturer>(lesson, "asistant"), lecturers));
    }

    @SpringBean
    private DisciplineRepository disciplineRepository;

    @SpringBean
    private RoomRepository roomRepository;

    @SpringBean
    private LecturerRepository lecturerRepository;
}
