package ua.dp.primat.schedule.view;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.StudentGroupRepository;

/**
 * View page for the Schedule portlet.
 * @author fdevelop
 */
public final class ViewSchedule extends WebPage {

    @SpringBean
    private StudentGroupRepository studentGroupRepository;

    //setup the total values of lessons per day
    private static final int LESSONSCOUNT = 6;

    //choosen student group
    private StudentGroup studentGroup;
    //list of all available StudentGroups
    private List<StudentGroup> groups;
    //list of all retrieved lessons
    private List<LessonQueryItem> lessons;

    private DropDownChoice<StudentGroup> groupChoice;
    private ListView<LessonQueryItem> lessonView;

    //constant of day names
    //TODO: reorganize it in a more common way
    private static final String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};

    //TEMPORARY method for returning the list of lessons
    //TODO: remove it, when there will be an entity repository with this operation
    private List<Lesson> getLessons(StudentGroup studentGroup) {
        List<Lesson> list  = new ArrayList<Lesson>();
        list.add(new Lesson("one lesson", Long.valueOf(0), Long.valueOf(0), 1, "lecturer1", Long.valueOf(2)));
        list.add(new Lesson("2 lesson", Long.valueOf(0), Long.valueOf(2), 1, "lecturer2", Long.valueOf(3)));
        list.add(new Lesson("3 lesson", Long.valueOf(1), Long.valueOf(2), 2, "lecturer3", Long.valueOf(1)));
        list.add(new Lesson("4 lesson", Long.valueOf(2), Long.valueOf(1), 5, "lecturer4", Long.valueOf(1)));
        list.add(new Lesson("5 lesson", Long.valueOf(2), Long.valueOf(2), 3, "lecturer5", Long.valueOf(1)));
        return list;
    }

    /**
     * Method, that puts lessons in a specific cells in schedule-table, where
     * row is represented as LessonQueryItem object.
     * TODO: sure, there could be a better solution. As an idea for database: multi-select query.
     * @param listLesson
     * @return list of extacly LESSONSCOUNT*2 items
     */
    private List<LessonQueryItem> getLessonQuery(List<Lesson> listLesson) {
        List<LessonQueryItem> list  = new ArrayList<LessonQueryItem>();
        for (int i=1;i<=LESSONSCOUNT;i++) {
            for (int j=0;j<2;j++) {
                list.add(new LessonQueryItem(i, Long.valueOf(j)));
            }
        }
        for (Lesson l : listLesson) {
            long pn = l.getPairnum();
            long wp = l.getWeekpos() % 2;
            list.get((int)(pn*2+wp)).setLessonForDay(l.getDay(), l);
        }
        return list;
    }

    /**
     * Constructor for page.
     */
    public ViewSchedule() {
        groups = studentGroupRepository.getGroups();
        if (!groups.isEmpty()) {
            studentGroup = groups.get(0);
            lessons = getLessonQuery(getLessons(studentGroup));
        }

        groupChoice = new DropDownChoice<StudentGroup>("group",
                new PropertyModel<StudentGroup>(this, "studentGroup"),
                new LoadableDetachableModelImpl(groups)) {

            @Override
            protected void onSelectionChanged(StudentGroup newSelection) {
                studentGroup = newSelection;
                lessons = getLessonQuery(getLessons(studentGroup));
                super.onSelectionChanged(newSelection);
            }

        };
        add(groupChoice);
        add(new Label("groupLabel", "Group:"));

        lessonView = new ScheduleListView("row", lessons);
        add(lessonView);
    }

    /**
     * LoadableDetachableModel for student groups combo.
     */
    static class LoadableDetachableModelImpl extends LoadableDetachableModel<List<StudentGroup>> {

        private final List<StudentGroup> groups;

        public LoadableDetachableModelImpl(List<StudentGroup> groups) {
            this.groups = groups;
        }

        @Override
        protected List<StudentGroup> load() {
            return groups;
        }
    }

    /**
     * ListView, that outputs generated LessonQueryItem list into the table.
     */
    private static class ScheduleListView extends ListView<LessonQueryItem> {

        public ScheduleListView(String string, List<? extends LessonQueryItem> list) {
            super(string, list);
        }

        @Override
        protected void populateItem(ListItem<LessonQueryItem> li) {
            final LessonQueryItem entry = li.getModelObject();
            
            //get the previous item.
            LessonQueryItem previous = null;
            if (li.getIndex() > 0) {
                previous = (LessonQueryItem)this.getList().get(li.getIndex()-1);
            }

            //format the number of lesson (because of table structure, which has
            //  two cells per day/lesson (depends on lesson's weekpos).
            Label labelNum = new Label("num", entry.getPairnum() + "");
            labelNum.setVisible(entry.getWeekpos() != 1);
            labelNum.add(new SimpleAttributeModifier("rowspan", "2"));
            li.add(labelNum);

            //output the lesson's info in SchedulePanel for every day
            for (int i=0;i<days.length;i++) {
                Panel labelDay = new ScheduleCell(days[i], entry.getLessonForDay(i+1));
                labelDay.setVisible(true);
                if ((previous != null) && (previous.getLessonForDay(i+1) != null)) {
                    labelDay.setVisible(previous.getLessonForDay(i+1).getWeekpos() != 2);
                }
                if ((entry.getLessonForDay(i+1) != null) && (entry.getLessonForDay(i+1).getWeekpos() == 2)) {
                    labelDay.add(new SimpleAttributeModifier("rowspan", "2"));
                }
                li.add(labelDay);
            }
        }

    }
}

