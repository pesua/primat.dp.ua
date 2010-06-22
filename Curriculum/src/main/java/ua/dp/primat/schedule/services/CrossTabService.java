package ua.dp.primat.schedule.services;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.WeekType;
import ua.dp.primat.schedule.view.crosstab.LessonQueryItem;

/**
 *
 * @author fdevelop
 */
@Service
public class CrossTabService {

    /**
     * Method, that puts lessons from database in a specific cells
     * into the schedule-table, where row is represented as
     * LessonQueryItem object.
     * @param listLesson - lessons to put
     * @return list of extacly lessonCount*2 items
     */
    public List<LessonQueryItem> getCrossTabItems(StudentGroup studentGroup, Long semester, int lessonCount, int weekTypeCount) {
        final List<LessonQueryItem> list = new ArrayList<LessonQueryItem>();

        //creates the crosstab structure
        for (int i=1;i<=lessonCount;i++) {
            // add extacly weekTypeCount items for each i
            list.add(new LessonQueryItem(i, WeekType.NUMERATOR));
            list.add(new LessonQueryItem(i, WeekType.DENOMINATOR));
        }

        //fill the crosstab with given data
        if (studentGroup != null) {
            List<Lesson> listLesson = lessonService.getLessons(studentGroup, semester);
            for (Lesson l : listLesson) {
                final int oneLessonNumber = l.getLessonNumber().intValue()-1;
                final int oneWeekType = l.getWeekType().ordinal() % weekTypeCount;
                //calculate absolute index of the item
                list.get(oneLessonNumber*weekTypeCount + oneWeekType).setLessonForDay(l.getDayOfWeek(), l);
            }
        }
        return list;
    }

    @Resource
    private LessonService lessonService;

}
