package ua.dp.primat.schedule.view.daybook;
import org.apache.wicket.markup.html.list.ListItem;
import ua.dp.primat.schedule.data.Lesson;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import ua.dp.primat.schedule.data.DayOfWeek;
import ua.dp.primat.schedule.data.WeekType;
import ua.dp.primat.utils.view.RefreshablePanel;

/**
 * View page for the Schedule portlet.
 * @author fdevelop
 */
public final class ViewDaybook extends RefreshablePanel {

    private static final long serialVersionUID = 1L;

    //week type to show
    private WeekType weekType = WeekType.NUMERATOR;

    //list of all retrieved lessons
    private List<Lesson> lessons;
    private List<DayInfo> listDataProv;
    private ListView<DayInfo> listView;

    public ViewDaybook(String id) {
        super(id);

        //prepare for the first display
        refreshView(lessons);

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

        //add the list view
        listView = new ListView<DayInfo>("dayRow", listDataProv) {

            @Override
            protected void populateItem(ListItem<DayInfo> li) {
                final DayInfo dayInfo = li.getModelObject();
                li.add(new Label("oneDayName", dayInfo.getDayOfWeek().toString()));
                li.add(new DayPanel("oneDayPanel", dayInfo.getListLessons()));
            }
        };
        add(listView);
    }

    /**
     * Method, which updates lessons, listDataProv and listView.
     * @param data  lessons to fill into the table
     */
    @Override
    public void refreshView(List<Lesson> data) {
        lessons = data;
        if (lessons != null) {
            listDataProv = new ArrayList<DayInfo>();
            for (DayOfWeek dw : DayOfWeek.values()) {
                if ((dw != DayOfWeek.SUNDAY) && (dw != DayOfWeek.SATURDAY)) {
                    listDataProv.add(new DayInfo(dw, getLessonsPerDay(lessons, dw, weekType)));
                }
            }
            if (listView != null) {
                listView.setList(listDataProv);
            }
        }
    }

    /**
     * TEMPORARY query, that returns lessons for one DayOfWeek and specified WeekType.
     * @param listLesson  data to search in
     * @param day
     * @param week
     * @return the list of Lesson, which are accepted by parameters
     */
    private List<Lesson> getLessonsPerDay(List<Lesson> listLesson, DayOfWeek day, WeekType week) {
        List<Lesson> list = new ArrayList<Lesson>();
        
        if (listLesson == null) {
            return list;
        }

        for (Lesson l : listLesson) {
            if ((l.getDayOfWeek() == day)
                    && ((l.getWeekType() == week)
                    || (l.getWeekType() == WeekType.BOTH))) {
                list.add(l);
            }
        }

        return list;
    }

}

