package ua.dp.primat.schedule.view.daybook;

import java.util.ArrayList;
import ua.dp.primat.domain.lesson.Lesson;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import java.util.List;
import javassist.SerialVersionUID;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import ua.dp.primat.domain.lesson.DayOfWeek;

/**
 * Panel, that outputs one schedule cell for specified Lesson.
 * It outputs the subject name, lecturer etc.
 * TODO: format and finish it
 * @author fdevelop
 */
public final class DayPanel extends Panel {

    public DayPanel(final String id, DayOfWeek day) {
        super(id);
        
        add(new Label("oneDayName", day.toString()));

        dayListView = new DayListView("row", new ArrayList<Lesson>());
        add(dayListView);
    }

    public void updateInfo(List<Lesson> listLesson) {
        if (listLesson == null) {
            dayListView.setList(new ArrayList<Lesson>());
        } else {
            dayListView.setList(listLesson);
        }
    }

    private ListView dayListView;

    private static final long serialVersionUID = 1L;

    /**
     * ListView, that outputs one day.
     */
    private static class DayListView extends ListView<Lesson> {

        public DayListView(String string, List<? extends Lesson> list) {
            super(string, list);
        }

        @Override
        protected void populateItem(ListItem<Lesson> li) {
            final Lesson entry = li.getModelObject();
            li.add(new Label("num", entry.getLessonNumber().toString()));

            if (entry.getLessonDescription() == null) {
                li.add(new Label("discipline", ""));
                li.add(new Label("teacher", ""));
            } else {
                final String strDisciplineWithType = String.format("%s (%s)", entry.getLessonDescription().getDiscipline().getName(), entry.getLessonDescription().getLessonType());
                li.add(new Label("discipline", strDisciplineWithType));
                li.add(new Label("teacher", entry.getLessonDescription().getLecturerNames()));
            }

            if (entry.getRoom() == null) {
                li.add(new Label("room", ""));
            } else {
                li.add(new Label("room", entry.getRoom().toString()));
            }
        }

    }

}