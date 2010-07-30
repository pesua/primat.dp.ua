package ua.dp.primat.schedule.view;

import org.apache.wicket.markup.html.WebPage;
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
        schedulePanel = new ViewSchedulePanel("tabs");
        add(schedulePanel);

        final Lecturer l = lecturerRepository.getAllLecturers().get(0);

        schedulePanel.refreshView(lessonService.getLessonsForLecturerBySemester(l, timeService.currentSemester()));
    }

    private ViewSchedulePanel schedulePanel;
    private static final long serialVersionUID = 1L;
    @SpringBean
    private LessonService lessonService;
    @SpringBean
    private TimeService timeService;


    @SpringBean
    private LecturerRepository lecturerRepository;
}

