package ua.dp.primat.schedule.view.daybook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ua.dp.primat.schedule.data.DayOfWeek;
import ua.dp.primat.schedule.data.Lesson;

/**
 *
 * @author fdevelop
 */
public class DayInfo implements Serializable {

    private DayOfWeek dayOfWeek;
    private List<Lesson> listLessons;

    public DayInfo(DayOfWeek dayOfWeek, List<Lesson> listLessons) {
        this.dayOfWeek = dayOfWeek;
        this.listLessons = new ArrayList<Lesson>(listLessons);
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public List<Lesson> getListLessons() {
        return listLessons;
    }

}
