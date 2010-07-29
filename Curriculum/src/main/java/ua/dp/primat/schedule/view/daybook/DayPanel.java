package ua.dp.primat.schedule.view.daybook;

import java.util.ArrayList;
import ua.dp.primat.domain.lesson.Lesson;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import java.util.List;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

/**
 * Panel, that outputs lessons for the one day.
 * Each row outputs the subject name, lecturer etc.
 * @author fdevelop
 */
public final class DayPanel extends Panel {

    /**
     * Constructor of wicket panel, which adds day name and list view
     * of lessons.
     * @param id
     * @param title
     */
    public DayPanel(final String id, String title) {
        super(id);

        add(new Label("oneDayName", title));

        dayListView = new DayListView("row", new ArrayList<Lesson>());
        add(dayListView);
    }

    /**
     * Updates list of lessons for this day-panel.
     * @param listLesson - new data
     */
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
     * ListView, that outputs lessons for one day.
     */
    private static class DayListView extends ListView<Lesson> {

        public DayListView(String string, List<? extends Lesson> list) {
            super(string, list);
        }

        @Override
        protected void populateItem(ListItem<Lesson> li) {
            final Lesson entry = li.getModelObject();

            final String disciplineValue = (entry.getLessonDescription() == null)
                    ? "" : String.format("%s (%s)",
                    entry.getLessonDescription().getDiscipline().getName(),
                    entry.getLessonDescription().getLessonType());
            final String teacherValue = (entry.getLessonDescription() == null)
                    ? "" : entry.getLessonDescription().getLecturerNames();
            final String roomValue = (entry.getRoom() == null) ? "" : entry.getRoom().toString();

            li.add(new Label("num", entry.getLessonNumber().toString()));
            li.add(new Label("discipline", disciplineValue));
            li.add(new Label("teacher", teacherValue));
            li.add(new Label("room", roomValue));
        }
        private static final long serialVersionUID = 2L;
    }
}
