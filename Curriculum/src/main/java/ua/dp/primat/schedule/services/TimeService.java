package ua.dp.primat.schedule.services;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.WeekType;

/**
 * Service which helps work with date or time extract information from it.
 * @author EniSh
 */
public class TimeService {

    public Semester currentSemester() {
        //TODO
        throw new NotImplementedException();
    }

    public DayOfWeek dayOfWeekByDate(Date date) {
        //TODO
        throw new NotImplementedException();
    }

    public DayOfWeek currentDay() {
        return dayOfWeekByDate(new Date());
    }

    public WeekType weekTypeByDate(Date date) {
        //TODO
        throw new NotImplementedException();
    }

    public WeekType currentWeekType() {
        return weekTypeByDate(new Date());
    }

    public Long lessonNumberByTime(Date date) {
        //TODO
        throw new NotImplementedException();
    }

    public Long currentLessonNumber() {
        return lessonNumberByTime(new Date());
    }

    /**
     * generate 8 semesters for given group.
     * @param group
     * @return returns list which consist 8 semesters which are corresponding semsters
     * with number 1, ..., 8 for group
     */
    public List<Semester> semestersForGroup(StudentGroup group) {
        //TODO
        throw new NotImplementedException();
    }

    /**
     * returns number of semester for given group.
     * E.g. for PZ-08-1, number of semester 2009-2010 2 is 4.
     * @param group
     * @param semester
     * @return number of semester
     */
    public Long semesterNumberForGroup(StudentGroup group, Semester semester) {
        //TODO
        throw new NotImplementedException();
    }
}
