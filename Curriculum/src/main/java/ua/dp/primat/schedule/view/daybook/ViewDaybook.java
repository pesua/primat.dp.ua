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
import ua.dp.primat.schedule.services.TimeService;
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
        weekType = timeService.currentWeekType();

        final String sWeek = "weekType";
        final DropDownWeekType weekTypeChoice = new DropDownWeekType(sWeek,
                new PropertyModel<WeekType>(this, sWeek),
                new LDModel(weekTypeValues));
        add(weekTypeChoice);

        //add the 6-day-week
        for (int i = 0; i < listDataPanel.length; i++) {
            listDataPanel[i] = new DayPanel("oneDay" + i, DayOfWeek.fromNumber(i).toString());
            add(listDataPanel[i]);
        }
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

    private static class LDModel extends LoadableDetachableModel<List<WeekType>> {

        LDModel(List<WeekType> list) {
            super(list);
            weekTypeValues = list;
        }

        @Override
        protected List<WeekType> load() {
            return weekTypeValues;
        }
        private List<WeekType> weekTypeValues;
        private static final long serialVersionUID = 1L;
    }

    private class DropDownWeekType extends DropDownChoice<WeekType> {

        DropDownWeekType(String id, PropertyModel<WeekType> propmodel, LoadableDetachableModel<List<WeekType>> ldmodel) {
            super(id, propmodel, ldmodel);
        }

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
        private static final long serialVersionUID = 1L;
    }

    /**
     * Update listDataPanel with the data from lessons.
     */
    private void updateDataPanel() {
        for (int i = 0; i < listDataPanel.length; i++) {
            listDataPanel[i].setLecturerVisible(isLecturerVisible());
            listDataPanel[i].setRoomVisible(isRoomVisible());
            listDataPanel[i].setGroupVisible(isGroupVisible());
            listDataPanel[i].updateInfo(lessonService.getLessonsPerDay(lessons,
                    DayOfWeek.fromNumber(i), weekType));
        }
    }
    //week type to show
    private WeekType weekType = WeekType.NUMERATOR;
    //list of all retrieved lessons
    private List<Lesson> lessons;
    private DayPanel[] listDataPanel = new DayPanel[DayOfWeek.values().length - 1];
    @SpringBean
    private LessonService lessonService;
    @SpringBean
    private TimeService timeService;

    private static final long serialVersionUID = 1L;
}

