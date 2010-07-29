package ua.dp.primat.schedule.widget;

import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.repositories.StudentGroupRepository;
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
        Request request = RequestCycle.get().getRequest();
        StudentGroup group = liferayUserService.studentGroupFrom(((ServletWebRequest)request).getHttpServletRequest());
        
        List<Lesson> lessons = lessonService.getLessonsForGroupBySemester(group, timeService.currentSemester());

        DayPanel todayPanel = new DayPanel("today", "today");
        todayPanel.updateInfo(lessonService.getLessonsPerDay(lessons, timeService.todayDayOfWeek(), WeekType.NUMERATOR));
        add(todayPanel);

        DayPanel tomorrowPanel = new DayPanel("tomorrow", "tomorrow");
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
