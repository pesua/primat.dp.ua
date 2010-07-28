package ua.dp.primat.schedule.widget;

import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.repositories.StudentGroupRepository;
import ua.dp.primat.schedule.services.LessonService;
import ua.dp.primat.schedule.view.daybook.DayPanel;

/**
 *
 * @author EniSh
 */
public final class ViewPage extends WebPage {

    public ViewPage() {
        super();
        StudentGroup group = groupRepository.getGroups().get(0);
        List<Lesson> lessons = lessonService.getLessonsForGroupAndSemester(group, 4L);
        today = new DayPanel("today", DayOfWeek.MONDAY);
        today.updateInfo(lessonService.getLessonsPerDay(lessons, DayOfWeek.MONDAY, WeekType.NUMERATOR));
        add(today);
        
        tomorrow = new DayPanel("tomorrow", DayOfWeek.TUESDAY);
        tomorrow.updateInfo(lessonService.getLessonsPerDay(lessons, DayOfWeek.TUESDAY, WeekType.NUMERATOR));
        add(tomorrow);
    }

    public ViewPage(PageParameters params) {
        this();
    }

    private DayPanel today;
    private DayPanel tomorrow;
    @SpringBean
    private LessonService lessonService;
    @SpringBean
    private StudentGroupRepository groupRepository;
}