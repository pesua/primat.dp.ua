package ua.dp.primat.schedule.admin;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.schedule.services.LessonItem;

/**
 *
 * @author Administrator
 */
public final class DayLessonsPanel extends Panel {

    public DayLessonsPanel(String id, DayOfWeek day, LessonItem[] lessons) {
        super(id);
        add(new Label("day", day.toString()));

        final List<LessonItem> l = Arrays.asList(lessons);

        add(new ListViewLesson("lesson", l) );
    }

    private static class ListViewLesson extends ListView<LessonItem> {

        ListViewLesson(String id, List<LessonItem> list) {
            super(id, list);
        }

        @Override
        protected void populateItem(ListItem<LessonItem> li) {
            final LessonItem item = li.getModelObject();
            li.add(new Label("number", String.valueOf(getNum())));
            li.add(new EditableScheduleItemPanel("lessonEditPanel", item));
        }

        private int getNum() {
            return num++;
        }
        private int num = 1;
        private static final long serialVersionUID = 1L;
    }
    private static final long serialVersionUID = 1L;
}
