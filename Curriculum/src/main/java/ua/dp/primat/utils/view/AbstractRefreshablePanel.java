package ua.dp.primat.utils.view;

import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;
import ua.dp.primat.domain.lesson.Lesson;

/**
 * Class, that extends wicket Panel with abstract method refreshView for panel's
 * data refreshing, that is provided by List object.
 * @author fdevelop
 */
public abstract class AbstractRefreshablePanel extends Panel {

    public AbstractRefreshablePanel(String id) {
        super(id);
    }

    /**
     * Method for updating data in Panel.
     * @param listLesson
     */
    public abstract void refreshView(List<Lesson> listLesson);
    

    public void setGroupVisible(boolean groupVisible) {
        this.groupVisible = groupVisible;
    }

    public void setLecturerVisible(boolean lecturerVisible) {
        this.lecturerVisible = lecturerVisible;
    }

    public void setRoomVisible(boolean roomVisible) {
        this.roomVisible = roomVisible;
    }
    
    protected boolean lecturerVisible = true;
    protected boolean roomVisible = true;
    protected boolean groupVisible = true;
}
