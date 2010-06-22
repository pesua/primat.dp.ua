package ua.dp.primat.utils.view;

import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.Lesson;

/**
 * Class, that extends wicket Panel with abstract method refreshView for panel's
 * data refreshing, that is provided by List object.
 * @author fdevelop
 */
public abstract class RefreshablePanel extends Panel {

    public RefreshablePanel(String id) {
        super(id);
    }

    /**
     * Method for updating data in Panel.
     * @param studentGroup
     * @param semester
     */
    public abstract void refreshView(StudentGroup pstudentGroup, Long psemester);

}
