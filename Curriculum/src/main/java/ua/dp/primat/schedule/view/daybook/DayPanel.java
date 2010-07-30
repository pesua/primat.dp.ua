package ua.dp.primat.schedule.view.daybook;

import java.util.ArrayList;
import ua.dp.primat.domain.lesson.Lesson;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import java.util.List;
import java.util.ResourceBundle;
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

        add(new Label("head.lecturer", locale.getString("table.caption.lecturer")) {

            @Override
            public boolean isVisible() {
                return lecturerVisible;
            }
        });
        add(new Label("head.room", locale.getString("table.caption.room")) {

            @Override
            public boolean isVisible() {
                return roomVisible;
            }
        });
        add(new Label("head.group", locale.getString("table.caption.group")) {

            @Override
            public boolean isVisible() {
                return groupVisible;
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

    public boolean isGroupVisible() {
        return groupVisible;
    }

    public void setGroupVisible(boolean groupVisible) {
        this.groupVisible = groupVisible;
    }

    public boolean isLecturerVisible() {
        return lecturerVisible;
    }

    public void setLecturerVisible(boolean lecturerVisible) {
        this.lecturerVisible = lecturerVisible;
    }

    public boolean isRoomVisible() {
        return roomVisible;
    }

    public void setRoomVisible(boolean roomVisible) {
        this.roomVisible = roomVisible;
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
            lecturer.setVisible(lecturerVisible);
            li.add(lecturer);

            final Label group = new Label("group", groupValue);
            group.setVisible(groupVisible);
            li.add(group);

            final Label room = new Label("room", roomValue);
            room.setVisible(roomVisible);
            li.add(room);
        }
        private static final long serialVersionUID = 2L;
    }
    private static ResourceBundle locale = ResourceBundle.getBundle("ua.dp.primat.schedule.view.DayPanel");
    private boolean lecturerVisible = true;
    private boolean roomVisible = true;
    private boolean groupVisible = true;
    private ListView dayListView;
    private static final long serialVersionUID = 1L;
}
