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
import ua.dp.primat.curriculum.data.Discipline;
import ua.dp.primat.schedule.data.Lecturer;
import ua.dp.primat.schedule.data.LecturerRepository;
import ua.dp.primat.schedule.data.Lesson;
import ua.dp.primat.schedule.data.Room;
import ua.dp.primat.schedule.data.RoomRepository;
import ua.dp.primat.schedule.services.EditableLesson;

/**
 *
 * @author Administrator
 */
public class EditableLesonPanel extends Panel {
    public EditableLesonPanel(String id, EditableLesson lesson) {
        super (id);
        List<Room> rooms = roomRepository.getRooms();
        //TODO get disciplines for the current group and semester
        List<Discipline> disciplines = new ArrayList<Discipline>();
        List<Lecturer> lecturers = lecturerRepository.getAllLecturers();

        add(new DropDownChoice<Discipline>("discipline", new PropertyModel<Discipline>(lesson, "discipline"), disciplines));
        add(new DropDownChoice<Room>("room", new PropertyModel<Room>(lesson, "room"), rooms));
        add(new DropDownChoice<Lecturer>("lecturer", new PropertyModel<Lecturer>(lesson, "lecturer"), lecturers));
        add(new DropDownChoice<Lecturer>("assistant", new PropertyModel<Lecturer>(lesson, "assistant"), lecturers));
    }



    @SpringBean
    private RoomRepository roomRepository;

    @SpringBean
    private LecturerRepository lecturerRepository;
}
