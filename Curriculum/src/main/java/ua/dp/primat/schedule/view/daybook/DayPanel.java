package ua.dp.primat.schedule.view.daybook;

import java.util.ArrayList;
import ua.dp.primat.schedule.data.Lesson;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import java.util.List;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

/**
 * Panel, that outputs one schedule cell for specified Lesson.
 * It outputs the subject name, lecturer etc.
 * TODO: format and finish it
 * @author fdevelop
 */
public final class DayPanel extends Panel {

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
            final String strDisciplineWithType = String.format("%s (%s)", entry.getLessonDescription().getDiscipline().getName(), entry.getLessonDescription().getLessonType());
            li.add(new Label("num", entry.getLessonNumber().toString()));
            li.add(new Label("discipline", strDisciplineWithType));
            li.add(new Label("teacher", entry.getLessonDescription().getLecturerNames()));
            li.add(new Label("room", entry.getRoom().toString()));
        }

    }

    public DayPanel(final String id, List<Lesson> list) {
        super(id);
        if (list == null) {
            list = new ArrayList<Lesson>();
        }
        add(new DayListView("row", list));
    }

}