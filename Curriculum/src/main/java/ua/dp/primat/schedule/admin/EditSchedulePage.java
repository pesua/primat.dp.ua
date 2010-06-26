package ua.dp.primat.schedule.admin;

import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.schedule.services.EditScheduleService;
import ua.dp.primat.schedule.services.LessonItem;
import ua.dp.primat.schedule.services.WeekLessonColection;

/**
 *
 * @author EniSh
 */
public final class EditSchedulePage extends WebPage {
    private static final long serialVersionUID = 1L;
    
    public EditSchedulePage(final StudentGroup group, final Long semester) {
        super ();
        editScheduleService.updateLists();

        add(new Label("group", group.toString()));
        add(new Label("semester", semester.toString()));
        final WeekLessonColection schedule = editScheduleService.getSchedule(group, semester);
        final List<LessonItem[]> days = schedule.getDayList();

        final Form editForm = new Form("editForm") {

            @Override
            protected void onSubmit() {
                editScheduleService.setSchedule(group, semester, schedule);
                setResponsePage(getApplication().getHomePage());
            }

        };
        editForm.add(new ListView<LessonItem[]>("days", days){
            private int dayNum = 0;
            
            @Override
            protected void populateItem(ListItem<LessonItem[]> li) {
                final LessonItem[] lessons = li.getModelObject();
                li.add(new DayLessonsPanel("dayLessons", DayOfWeek.fromNumber(dayNum++), lessons));
            }
            
        });
        add(editForm);
    }

    @SpringBean
    private EditScheduleService editScheduleService;
}

