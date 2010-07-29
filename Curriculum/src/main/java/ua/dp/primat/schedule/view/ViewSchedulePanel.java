package ua.dp.primat.schedule.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.schedule.view.crosstab.ViewCrosstab;
import ua.dp.primat.schedule.view.daybook.ViewDaybook;
import ua.dp.primat.utils.view.AbstractRefreshablePanel;

/**
 *
 * @author EniSh
 */
public final class ViewSchedulePanel extends Panel {

    public ViewSchedulePanel(String id) {
        super(id);
        languageLoad();

        final List<ITab> tabs = new ArrayList<ITab>();
        tabs.add(new AbstractTab(new Model<String>(tabDaybookText)) {

            @Override
            public Panel getPanel(String panelId) {
                daybookPanel = new ViewDaybook(panelId);
                daybookPanel.refreshView(lessons);
                return daybookPanel;
            }
        });
        tabs.add(new AbstractTab(new Model<String>(tabScheduleText)) {

            @Override
            public Panel getPanel(String panelId) {
                schedulePanel = new ViewCrosstab(panelId);
                schedulePanel.refreshView(lessons);
                return schedulePanel;
            }
        });
        add(new AjaxTabbedPanel("tabs", tabs));
    }

    public void refreshView(List<Lesson> lessons) {
        this.lessons = lessons;
        if (schedulePanel != null) {
            schedulePanel.refreshView(lessons);
        }
        if (daybookPanel != null) {
            daybookPanel.refreshView(lessons);
        }
    }

    /**
     * Load language resources from assigned to page .properties file.
     */
    private void languageLoad() {
        final ResourceBundle bundle = ResourceBundle.getBundle("ua.dp.primat.schedule.view.ViewHomePage");
        tabScheduleText = bundle.getString("tab.crosstab");
        tabDaybookText = bundle.getString("tab.daybook");
    }

    private AbstractRefreshablePanel schedulePanel;
    private AbstractRefreshablePanel daybookPanel;
    private List<Lesson> lessons = Collections.EMPTY_LIST;

    private String tabScheduleText;
    private String tabDaybookText;

    private static final long serialVersionUID = 1L;
}
