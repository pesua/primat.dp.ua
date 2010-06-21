package ua.dp.primat.schedule.view.crosstab;
import ua.dp.primat.domain.lesson.Lesson;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.utils.view.RefreshablePanel;

/**
 * View page for the Schedule portlet.
 * @author fdevelop
 */
public final class ViewCrosstab extends RefreshablePanel {

    private static final long serialVersionUID = 1L;
    //setup the total values of lessons per day
    private static final int LESSONSCOUNT = 6;
    private static final int WEEKTYPECOUNT = 2;

    /**
     * ListView, that outputs generated LessonQueryItem list into the table.
     */
    private static class ScheduleListView extends ListView<LessonQueryItem> {

        private final SimpleAttributeModifier rowspanAttrModifier = new SimpleAttributeModifier("rowspan", "2");

        public ScheduleListView(String string, List<? extends LessonQueryItem> list) {
            super(string, list);
        }

        @Override
        protected void populateItem(ListItem<LessonQueryItem> li) {
            final LessonQueryItem entry = li.getModelObject();

            //get the previous item.
            LessonQueryItem previous;
            if (li.getIndex() > 0) {
                previous = (LessonQueryItem)this.getList().get(li.getIndex()-1);
            } else {
                previous = null;
            }

            //format the number of lesson (because of table structure, which has
            //  two cells per day/lesson (depends on lesson's weekpos).
            final Label labelNum = new Label(NUM_COLUMN, Integer.toString(entry.getLessonNumber()));

            //set visible, which is depended on entry's weekType
            labelNum.setVisible(entry.getWeekType() != WeekType.DENOMINATOR);

            //modify the rowspan property (it will be applied to the visible items)
            labelNum.add(rowspanAttrModifier);

            li.add(labelNum);

            //output the lesson's info in SchedulePanel for every day
            for (int i=0;i<DAY_WICKET_KEYS.length;i++) {
                final DayOfWeek dayOfWeek = DayOfWeek.values()[i];
                final Panel labelDay = new ScheduleCell(DAY_WICKET_KEYS[i], entry.getLessonForDay(dayOfWeek));
                labelDay.setVisible(true);
                if ((previous != null) && (previous.getLessonForDay(dayOfWeek) != null)) {
                    labelDay.setVisible(previous.getLessonForDay(dayOfWeek).getWeekType() != WeekType.BOTH);
                }
                if ((entry.getLessonForDay(dayOfWeek) != null) && (entry.getLessonForDay(dayOfWeek).getWeekType() == WeekType.BOTH)) {
                    labelDay.add(rowspanAttrModifier);
                }
                //hide sunday & saturday for now
                if ((dayOfWeek == DayOfWeek.SUNDAY) || (dayOfWeek == DayOfWeek.SATURDAY)) {
                    labelDay.setVisible(false);
                }

                li.add(labelDay);
            }
        }

    }

    //constant of for wicket
    private static final String ROW_MARKUP = "row";
    private static final String NUM_COLUMN = "num";
    private static final String DAYNAME_WICKET = "caption_";
    private static final String[] DAY_WICKET_KEYS = {"monday", "tuesday", "wednesday", "thursday", "friday"};

    //list and view of all retrieved lessons
    private List<LessonQueryItem> lessons;
    private ListView<LessonQueryItem> lessonView;

    /**
     * Constructor for page, add ScheduleListView onto the panel
     */
    public ViewCrosstab(String id) {
        super(id);

        for (int i=0;i<DAY_WICKET_KEYS.length;i++) {
            final Label labelDay = new Label(DAYNAME_WICKET + DAY_WICKET_KEYS[i], DayOfWeek.fromNumber(i).toString());
            add(labelDay);
        }

        lessonView = new ScheduleListView(ROW_MARKUP, lessons);
        add(lessonView);
    }

    /**
     * Method, which updates lessons and lessonView.
     * @param data  lessons to fill into the table
     */
    @Override
    public void refreshView(List<Lesson> data) {
        lessons = getLessonQuery(data, LESSONSCOUNT, WEEKTYPECOUNT);
        if (lessonView != null) {
            lessonView.setList(lessons);
        }
    }

    /**
     * Method, that puts lessons in a specific cells in schedule-table, where
     * row is represented as LessonQueryItem object.
     * TODO: there could be a better solution.
     * @param listLesson  lessons to put
     * @return list of extacly LESSONSCOUNT*2 items
     */
    private List<LessonQueryItem> getLessonQuery(List<Lesson> listLesson, int lessonCount, int weekTypeCount) {
        final List<LessonQueryItem> list  = new ArrayList<LessonQueryItem>();

        //creates the crosstab structure
        for (int i=1;i<=lessonCount;i++) {
            // add extacly weekTypeCount items for each i
            list.add(new LessonQueryItem(i, WeekType.NUMERATOR));
            list.add(new LessonQueryItem(i, WeekType.DENOMINATOR));
        }

        //fill the crosstab with given data
        for (Lesson l : listLesson) {
            final long oneLessonNumber = l.getLessonNumber()-1;
            final long oneWeekType = l.getWeekType().ordinal() % weekTypeCount;
            //calculate absolute index of the item
            list.get((int)(oneLessonNumber*weekTypeCount + oneWeekType)).setLessonForDay(l.getDayOfWeek(), l);
        }
        return list;
    }

}

