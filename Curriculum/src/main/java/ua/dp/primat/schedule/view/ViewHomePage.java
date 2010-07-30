package ua.dp.primat.schedule.view;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.schedule.services.LessonService;
import ua.dp.primat.utils.view.AbstractChoosePanel;

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
        schedulePanel = new ViewSchedulePanel("lessonView");
        schedulePanel.setGroupVisible(false);
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
