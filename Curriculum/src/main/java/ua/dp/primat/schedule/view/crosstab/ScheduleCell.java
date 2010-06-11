package ua.dp.primat.schedule.view.crosstab;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import ua.dp.primat.schedule.data.Lesson;
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
            add(new Label("cellSubject", ""));
            add(new Label("cellType", ""));
            add(new Label("cellLecturer", ""));
            add(new Label("cellRoom", ""));
            add(new SimpleAttributeModifier("style", "background-color: #F6FFEF"));
        } else {
            add(new Label("cellSubject", lesson.getLessonDescription().getDiscipline().getName()));
            add(new Label("cellType", "("+lesson.getLessonDescription().getLessonType().toString()+")"));
            add(new Label("cellLecturer", lesson.getLessonDescription().getLecturerNames()));
            add(new Label("cellRoom", lesson.getRoom().toString()));
        }
    }

}

