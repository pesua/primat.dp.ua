package ua.dp.primat.schedule.widget;

import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.schedule.services.LessonService;
import ua.dp.primat.schedule.services.LiferayUserService;
import ua.dp.primat.schedule.services.TimeService;
import ua.dp.primat.schedule.view.daybook.DayPanel;

/**
 *
 * @author EniSh
 */
public final class ViewPage extends WebPage {

    public ViewPage() {
        super();
        final Request request = RequestCycle.get().getRequest();
        final HttpServletRequest req = ((ServletWebRequest) request).getHttpServletRequest();

        List<Lesson> lessons;

        final Lecturer lecturer = liferayUserService.lecturerFrom(req);
        if (lecturer != null) {
            lessons = lessonService.getLessonsForLecturerBySemester(lecturer, timeService.currentSemester());
        } else {
            final StudentGroup group = liferayUserService.studentGroupFrom(req);
            if (group != null) {
                lessons = lessonService.getLessonsForGroupBySemester(group, timeService.currentSemester());

            } else {
                throw new RestartResponseAtInterceptPageException(NoSchedulePage.class);
            }
        }

        ResourceBundle locale = ResourceBundle.getBundle("ua.dp.primat.schedule.widget.ViewPage");

        final DayPanel todayPanel = new DayPanel("todayPanel", locale.getString("today"));
        todayPanel.updateInfo(lessonService.getLessonsPerDay(lessons, timeService.todayDayOfWeek(), WeekType.NUMERATOR));
        add(todayPanel);

        final DayPanel tomorrowPanel = new DayPanel("tomorrowPanel", locale.getString("tomorrow"));
        tomorrowPanel.updateInfo(lessonService.getLessonsPerDay(lessons, timeService.tomorrowDayOfWeek(), WeekType.NUMERATOR));
        add(tomorrowPanel);
    }
    @SpringBean
    private LessonService lessonService;
    @SpringBean
    private LiferayUserService liferayUserService;
    @SpringBean
    private TimeService timeService;
    private static final long serialVersionUID = 1L;
}
