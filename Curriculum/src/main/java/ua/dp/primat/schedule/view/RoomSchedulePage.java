package ua.dp.primat.schedule.view;

import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.Room;
import ua.dp.primat.repositories.RoomRepository;
import ua.dp.primat.schedule.services.LessonService;
import ua.dp.primat.schedule.services.TimeService;

/**
 *
 * @author EniSh
 */
public final class RoomSchedulePage extends WebPage {
    public RoomSchedulePage() {
        super ();final List<Room> lecturers = roomRepository.getRooms();
        room = lecturers.get(0);

        add(new DropDownChoice<Room>(
                "roomChoise",
                new PropertyModel<Room>(this, "room"),
                lecturers) {

            @Override
            protected void onSelectionChanged(Room newSelection) {
                super.onSelectionChanged(newSelection);
                schedulePanel.refreshView(lessonService.getLessonForRoomBySemester(room, timeService.currentSemester()));
            }

            @Override
            protected boolean wantOnSelectionChangedNotifications() {
                return true;
            }
        });

        schedulePanel = new ViewSchedulePanel("lessonView");
        schedulePanel.setRoomVisible(false);
        add(schedulePanel);

        schedulePanel.refreshView(lessonService.getLessonForRoomBySemester(room, timeService.currentSemester()));
    }
    private ViewSchedulePanel schedulePanel;
    @SpringBean
    private LessonService lessonService;
    @SpringBean
    private TimeService timeService;
    private Room room;
    @SpringBean
    private RoomRepository roomRepository;

    private static final long serialVersionUID = 1L;
}

