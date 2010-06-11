package ua.dp.primat.schedule.admin;

import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.schedule.services.EditScheduleService;
import ua.dp.primat.schedule.services.LessonItem;

/**
 *
 * @author EniSh
 */
public final class EditSchedulePage extends WebPage {
    public EditSchedulePage(StudentGroup group, Long semester) {
        super ();
        add(new Label("group", group.toString()));
        add(new Label("semester", semester.toString()));
        List<LessonItem[]> days = editScheduleService.getSchedule(group, semester).getDayList();

        add(new ListView<LessonItem[]>("days", days){

            @Override
            protected void populateItem(ListItem<LessonItem[]> li) {
                LessonItem[] lessons = li.getModelObject();
                li.add(new DayLessonsPanel("dayLessons", lessons));
            }
            
        });
    }

    @SpringBean
    private EditScheduleService editScheduleService;
}

