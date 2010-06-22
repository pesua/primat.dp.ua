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
import ua.dp.primat.utils.view.RefreshablePanel;

/**
 * View page for the Schedule portlet.
 * @author fdevelop
 */
public final class ViewDaybook extends RefreshablePanel {

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
        final DropDownChoice<WeekType> weekTypeChoice = new DropDownChoice<WeekType>("weekType",
                new PropertyModel<WeekType>(this, "weekType"),
                new LoadableDetachableModel<List<WeekType>>(weekTypeValues) {

            @Override
            protected List<WeekType> load() {
                return weekTypeValues;
            }

        }) {

            @Override
            protected void onSelectionChanged(WeekType newSelection) {
                weekType = newSelection;

                refreshView(lessons);
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
            listDataPanel[i] = new DayPanel("oneDay"+i, DayOfWeek.fromNumber(i));
            add(listDataPanel[i]);
        }

        //prepare for the first display
        refreshView(null);
    }

    /**
     * Method, which updates lessons, listDataProv and listView.
     * @param data - lessons to fill into the table
     */
    @Override
    public void refreshView(List<Lesson> data) {
        lessons = data;
        for (int i = 0; i<listDataPanel.length; i++) {
            listDataPanel[i].updateInfo(lessonService.getLessonsPerDay(lessons, DayOfWeek.fromNumber(i), weekType));
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

