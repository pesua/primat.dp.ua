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
import ua.dp.primat.utils.view.ChoosePanel;
import ua.dp.primat.utils.view.RefreshablePanel;

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
        languageLoad();

        // create the tabbed panel
        final List<ITab> tabs = new ArrayList<ITab>();
        tabs.add(new AbstractTab(new Model<String>(tabDaybookText)) {
                @Override
                public Panel getPanel(String panelId)
                {
                    daybookPanel = new ViewDaybook(panelId);
                    daybookPanel.refreshView(queryResult);
                    return daybookPanel;
                }
        });

        tabs.add(new AbstractTab(new Model<String>(tabScheduleText)) {
                @Override
                public Panel getPanel(String panelId)
                {
                    schedulePanel = new ViewCrosstab(panelId);
                    schedulePanel.refreshView(queryResult);
                    return schedulePanel;
                }
        });
        add(new AjaxTabbedPanel("tabs", tabs));

        //create chooser for group and semester
        final ChoosePanel choosePanel = new ChoosePanel("choosePanel") {

            @Override
            protected void executeAction(StudentGroup studentGroup, Long semester) {
                queryResult = lessonService.getLessonsForGroupAndSemester(studentGroup, semester);
                if (schedulePanel != null) {
                    schedulePanel.refreshView(queryResult);
                }
                if (daybookPanel != null) {
                    daybookPanel.refreshView(queryResult);
                }
            }
        };
        add(choosePanel);
    }

    /**
     * Load language resources from assigned to page .properties file.
     */
    private void languageLoad() {
        final ResourceBundle bundle = ResourceBundle.getBundle("ua.dp.primat.schedule.view.ViewHomePage");
        tabScheduleText = bundle.getString("tab.crosstab");
        tabDaybookText = bundle.getString("tab.daybook");
    }

    private RefreshablePanel schedulePanel;
    private RefreshablePanel daybookPanel;

    private String tabScheduleText;
    private String tabDaybookText;

    private List<Lesson> queryResult;

    @SpringBean
    private LessonService lessonService;

    private static final long serialVersionUID = 1L;

}