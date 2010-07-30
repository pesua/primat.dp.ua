package ua.dp.primat.schedule.view;

import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.repositories.LecturerRepository;
import ua.dp.primat.schedule.services.LessonService;
import ua.dp.primat.schedule.services.TimeService;

/**
 *
 * @author EniSh
 */
public final class LecturerSchedulePage extends WebPage {
    public LecturerSchedulePage() {
        super ();
        List<Lecturer> lecturers = lecturerRepository.getAllLecturers();
        lecturer = lecturers.get(0);
        
        add(new DropDownChoice<Lecturer>(
                "lecturerChoise", 
                new PropertyModel<Lecturer>(this, "lecturer"), 
                lecturers) {

            @Override
            protected void onSelectionChanged(Lecturer newSelection) {
                super.onSelectionChanged(newSelection);
                schedulePanel.refreshView(lessonService.getLessonsForLecturerBySemester(lecturer, timeService.currentSemester()));
            }
            
        });
        
        schedulePanel = new ViewSchedulePanel("tabs");
        add(schedulePanel);
    }

    private ViewSchedulePanel schedulePanel;
    private static final long serialVersionUID = 1L;
    @SpringBean
    private LessonService lessonService;
    @SpringBean
    private TimeService timeService;

    private Lecturer lecturer;

    @SpringBean
    private LecturerRepository lecturerRepository;
}

