package ua.dp.primat.schedule.view.daybook;
import ua.dp.primat.domain.lesson.Lesson;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.schedule.services.LessonService;
import ua.dp.primat.utils.view.AbstractRefreshablePanel;

/**
 * View page for the Schedule portlet.
 * @author fdevelop
 */
public final class ViewDaybook extends AbstractRefreshablePanel {

    /**
     * Constructor, which creates wicket panel with 6 inner day-panels and
     * the combobox to select the WeekType.
     * @param id
     */
    public ViewDaybook(String id) {
        super(id);

        //add the weekType choice group combo
        final List<WeekType> weekTypeValues = new ArrayList<WeekType>();
        weekTypeValues.add(WeekType.NUMERATOR);
        weekTypeValues.add(WeekType.DENOMINATOR);
        final String sWeek = new String("weekType");
        final DropDownChoice<WeekType> weekTypeChoice = new DropDownChoice<WeekType>(sWeek,
                new PropertyModel<WeekType>(this, sWeek),
                new LoadableDetachableModel<List<WeekType>>(weekTypeValues) {

            @Override
            protected List<WeekType> load() {
                return weekTypeValues;
            }

        }) {

            @Override
            protected void onSelectionChanged(WeekType newSelection) {
                weekType = newSelection;
                updateDataPanel();
                super.onSelectionChanged(newSelection);
            }

            @Override
            protected boolean wantOnSelectionChangedNotifications() {
                return true;
            }

        };
        add(weekTypeChoice);

        //add the 6-day-week
        for (int i = 0; i < listDataPanel.length; i++) {
            listDataPanel[i] = new DayPanel("oneDay" + i, DayOfWeek.fromNumber(i));
            add(listDataPanel[i]);
        }

        //prepare for the first display
        //refreshView(null);
    }

    /**
     * Set new data for the lessons from listLessons and update view.
     * @param listLesson
     */
    @Override
    public void refreshView(List<Lesson> listLesson) {
        lessons = listLesson;
        updateDataPanel();
    }

    /**
     * Update listDataPanel with the data from lessons.
     */
    private void updateDataPanel() {
        for (int i = 0; i<listDataPanel.length; i++) {
            listDataPanel[i].updateInfo(lessonService.getLessonsPerDay(lessons,
                    DayOfWeek.fromNumber(i), weekType));
        }
    }

    //week type to show
    private WeekType weekType = WeekType.NUMERATOR;

    //list of all retrieved lessons
    private List<Lesson> lessons;
    private DayPanel[] listDataPanel = new DayPanel[DayOfWeek.values().length-1];

    @SpringBean
    private LessonService lessonService;

    private static final long serialVersionUID = 1L;

}

