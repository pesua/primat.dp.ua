package ua.dp.primat.schedule.view.daybook;

import java.util.ArrayList;
import ua.dp.primat.domain.lesson.Lesson;
import org.apache.wicket.markup.html.basic.Label;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import ua.dp.primat.schedule.view.ShedulePanel;

/**
 * Panel, that outputs lessons for the one day.
 * Each row outputs the subject name, lecturer etc.
 * @author fdevelop
 */
public final class DayPanel extends ShedulePanel {

    /**
     * Constructor of wicket panel, which adds day name and list view
     * of lessons.
     * @param id
     * @param title
     */
    public DayPanel(final String id, String title) {
        super(id);

        add(new Label("oneDayName", title));

        add(new Label("head.lecturer", locale.getString("table.caption.lecturer")) {

            @Override
            public boolean isVisible() {
                return isLecturerVisible();
            }
        });
        add(new Label("head.room", locale.getString("table.caption.room")) {

            @Override
            public boolean isVisible() {
                return isRoomVisible();
            }
        });
        add(new Label("head.group", locale.getString("table.caption.group")) {

            @Override
            public boolean isVisible() {
                return isGroupVisible();
            }
        });

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

    /**
     * ListView, that outputs lessons for one day.
     */
    private class DayListView extends ListView<Lesson> {

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
            final String groupValue = ((entry.getLessonDescription() == null)
                    || (entry.getLessonDescription().getStudentGroup() == null))
                    ? "" : entry.getLessonDescription().getStudentGroup().toString();
            final String roomValue = (entry.getRoom() == null) ? "" : entry.getRoom().toString();

            li.add(new Label("num", entry.getLessonNumber().toString()));
            li.add(new Label("discipline", disciplineValue));

            final Label lecturer = new Label("teacher", teacherValue);
            lecturer.setVisible(isLecturerVisible());
            li.add(lecturer);

            final Label group = new Label("group", groupValue);
            group.setVisible(isGroupVisible());
            li.add(group);

            final Label room = new Label("room", roomValue);
            room.setVisible(isRoomVisible());
            li.add(room);
        }
        private static final long serialVersionUID = 2L;
    }
    private static ResourceBundle locale = ResourceBundle.getBundle("ua.dp.primat.schedule.view.daybook.DayPanel");
    private ListView dayListView;
    private static final long serialVersionUID = 1L;
}
