package ua.dp.primat.schedule.view.crosstab;
import ua.dp.primat.domain.lesson.Lesson;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Panel, that outputs one schedule cell for specified Lesson.
 * It outputs the subject name, lecturer etc.
 * @author fdevelop
 */
public final class ScheduleCell extends Panel {

    public ScheduleCell(final String id, Lesson lesson) {
        super(id);

        final String cellSubject = ((lesson == null) ? "" : lesson.getLessonDescription().getDiscipline().getName());
        final String cellType = ((lesson == null) ? "" : "("+lesson.getLessonDescription().getLessonType()+")");
        final String cellLecturer = ((lesson == null) ? "" : lesson.getLessonDescription().getLecturerNames());
        final String cellGroup = ((lesson == null) || (lesson.getLessonDescription() == null))
                ? "" : lesson.getLessonDescription().getStudentGroup().toString();
        final String cellRoom = ((lesson == null) ? "" : lesson.getRoom().toString());
        
        add(new Label("cellSubject", cellSubject));
        add(new Label("cellType", cellType));
        add(new Label("cellLecturer", cellLecturer) {

            @Override
            public boolean isVisible() {
                return lecturerVisible;
            }
        });
        add(new Label("cellGroup", cellGroup) {

            @Override
            public boolean isVisible() {
                return groupVisible;
            }
        });
        add(new Label("cellRoom", cellRoom) {

            @Override
            public boolean isVisible() {
                return roomVisible;
            }
        });
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
    
    private boolean lecturerVisible = true;
    private boolean roomVisible = true;
    private boolean groupVisible = true;

    private static final long serialVersionUID = 1L;
}

