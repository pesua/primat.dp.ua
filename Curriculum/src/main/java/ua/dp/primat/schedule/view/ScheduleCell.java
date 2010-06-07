package ua.dp.primat.schedule.view;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Panel, that outputs one schedule cell for specified Lesson.
 * It outputs the subject name, lecturer etc.
 * TODO: format and finish it
 * @author fdevelop
 */
public final class ScheduleCell extends Panel {

    public ScheduleCell(final String id, Lesson lesson) {
        super(id);
        if (lesson == null) {
            add(new Label("cellSubject", "-"));
            add(new Label("cellLecturer", "-"));
        } else {
            add(new Label("cellSubject", lesson.getSubject_name()));
            add(new Label("cellLecturer", lesson.getLecturer()));
        }
    }

}

