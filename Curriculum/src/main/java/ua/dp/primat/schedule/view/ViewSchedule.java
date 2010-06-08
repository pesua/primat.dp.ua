package ua.dp.primat.schedule.view;
import ua.dp.primat.schedule.data.Lesson;
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
import ua.dp.primat.curriculum.data.Cathedra;
import ua.dp.primat.schedule.data.DayOfWeek;
import ua.dp.primat.curriculum.data.Discipline;
import ua.dp.primat.schedule.data.Lecturer;
import ua.dp.primat.schedule.data.LessonDescription;
import ua.dp.primat.schedule.data.LessonType;
import ua.dp.primat.schedule.data.Room;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.StudentGroupRepository;
import ua.dp.primat.schedule.data.WeekType;

/**
 * View page for the Schedule portlet.
 * @author fdevelop
 */
public final class ViewSchedule extends WebPage {

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

        final SimpleAttributeModifier rowspanAttrModifier = new SimpleAttributeModifier("rowspan", "2");

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
            final Label labelNum = new Label("num", Integer.toString(entry.getLessonNumber()));

            //set visible, which is depended on entry's weekType
            labelNum.setVisible(entry.getWeekType() != WeekType.DENOMINATOR);

            //modify the rowspan property (it will be applied to the visible items)
            labelNum.add(rowspanAttrModifier);
            
            li.add(labelNum);

            //output the lesson's info in SchedulePanel for every day
            for (int i=0;i<DAYKEYS.length;i++) {
                final DayOfWeek dayOfWeek = DayOfWeek.values()[i];
                final Panel labelDay = new ScheduleCell(DAYKEYS[i], entry.getLessonForDay(dayOfWeek));
                labelDay.setVisible(true);
                if ((previous != null) && (previous.getLessonForDay(dayOfWeek) != null)) {
                    labelDay.setVisible(previous.getLessonForDay(dayOfWeek).getWeekType() != WeekType.BOTH);
                }
                if ((entry.getLessonForDay(dayOfWeek) != null) && (entry.getLessonForDay(dayOfWeek).getWeekType() == WeekType.BOTH)) {
                    labelDay.add(rowspanAttrModifier);
                }
                li.add(labelDay);
            }
        }

    }

    private static final long serialVersionUID = 1L;

    //setup the total values of lessons per day
    private static final int LESSONSCOUNT = 6;

    //constant of day names (for wicket)
    //TODO: reorganize it in a more common way
    private static final String[] DAYKEYS = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};

    @SpringBean
    private StudentGroupRepository studentGroupRepository;

    //choosen student group
    private StudentGroup studentGroup;
    //list of all retrieved lessons
    private List<LessonQueryItem> lessons;

    //TEMPORARY method for returning the list of lessons
    //TODO: remove it, when there will be an entity repository with this operation
    @SuppressWarnings("unchecked")
    private List<Lesson> getLessons(StudentGroup studentGroup) {
        List<Lesson> list  = new ArrayList<Lesson>();
        
        Cathedra cathedra = new Cathedra();
        cathedra.setName("Math.EOM");
        
        Room room45 = new Room(Long.valueOf(3), Long.valueOf(45));
        Room room31 = new Room(Long.valueOf(3), Long.valueOf(31));

        Discipline d1 = new Discipline("Database", cathedra);
        Discipline d2 = new Discipline("ArchEOM", cathedra);

        Lecturer teacher1 = new Lecturer("Mashenko Leonid Vladimirovich", cathedra);
        Lecturer teacher2 = new Lecturer("Efimov Viktor Nikolaevich", cathedra);
        Lecturer teacher3 = new Lecturer("Bulana Tatyana Mihailovna", cathedra);
        Lecturer teacher4 = new Lecturer("Archangelska Uliya Mihailovna", cathedra);

        LessonDescription ld1 = new LessonDescription(d1, studentGroup, Long.valueOf(4), LessonType.LECTURE, teacher1, null);
        LessonDescription ld2 = new LessonDescription(d2, studentGroup, Long.valueOf(4), LessonType.LECTURE, teacher2, null);
        LessonDescription ld3 = new LessonDescription(d2, studentGroup, Long.valueOf(4), LessonType.LABORATORY, teacher3, teacher4);

        list.add(new Lesson(Long.valueOf(3), WeekType.BOTH, DayOfWeek.MONDAY, room31, ld1));
        list.add(new Lesson(Long.valueOf(3), WeekType.BOTH, DayOfWeek.TUESDAY, room31, ld2));
        list.add(new Lesson(Long.valueOf(4), WeekType.NUMERATOR, DayOfWeek.THURSDAY, room45, ld3));
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
        final List<LessonQueryItem> list  = new ArrayList<LessonQueryItem>();
        for (int i=1;i<=LESSONSCOUNT;i++) {
            list.add(new LessonQueryItem(i, WeekType.NUMERATOR));
            list.add(new LessonQueryItem(i, WeekType.DENOMINATOR));
        }

        final int WEEKTYPECOUNT = 2;
        long oneLessonNumber = 0;
        long oneWeekType = 0;
        for (Lesson l : listLesson) {
            oneLessonNumber = l.getLessonNumber()-1;
            oneWeekType = l.getWeekType().ordinal() % WEEKTYPECOUNT;
            //calculate absolute index of the item
            list.get((int)(oneLessonNumber*WEEKTYPECOUNT + oneWeekType)).setLessonForDay(l.getDayOfWeek(), l);
        }
        return list;
    }

    /**
     * Constructor for page.
     */
    public ViewSchedule() {
        super();

        final List<StudentGroup> groups = studentGroupRepository.getGroups();
        if (!groups.isEmpty()) {
            studentGroup = groups.get(0);
            lessons = getLessonQuery(getLessons(studentGroup));
        }

        final DropDownChoice<StudentGroup> groupChoice = new DropDownChoice<StudentGroup>("group",
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

        final ListView<LessonQueryItem> lessonView = new ScheduleListView("row", lessons);
        add(lessonView);
    }

}

