package ua.dp.primat.schedule.view;

import org.apache.wicket.markup.html.WebPage;

/**
 *
 * @author EniSh
 */
public final class LecturerSchedulePage extends WebPage {
    public LecturerSchedulePage() {
        super ();
        schedulePanel = new ViewSchedulePanel("tabs");
        add(schedulePanel);
    }

    ViewSchedulePanel schedulePanel;
}

