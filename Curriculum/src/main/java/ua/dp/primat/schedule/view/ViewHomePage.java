package ua.dp.primat.schedule.view;

import ua.dp.primat.schedule.view.daybook.ViewDaybook;
import ua.dp.primat.schedule.view.crosstab.ViewCrosstab;
import ua.dp.primat.domain.lesson.Lesson;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.schedule.services.LessonService;
import ua.dp.primat.utils.view.AbstractChoosePanel;
import ua.dp.primat.utils.view.AbstractRefreshablePanel;

/**
 * View page for the Schedule portlet.
 * @author fdevelop
 */
public final class ViewHomePage extends WebPage {

    /**
     * Contructor for the home page, which adds tabs and choose panel.
     */
    public ViewHomePage() {
        super();
        schedulePanel = new ViewSchedulePanel("tabs");
        add(schedulePanel);

        //create chooser for group and semester
        final AbstractChoosePanel choosePanel = new AbstractChoosePanel("choosePanel") {

            @Override
            protected void executeAction(StudentGroup studentGroup, Long semester) {
                schedulePanel.refreshView(lessonService.getLessonsForGroupBySemester(studentGroup, semester));
            }
        };
        add(choosePanel);
    }
    private ViewSchedulePanel schedulePanel;

    @SpringBean
    private LessonService lessonService;
    private static final long serialVersionUID = 1L;
}
