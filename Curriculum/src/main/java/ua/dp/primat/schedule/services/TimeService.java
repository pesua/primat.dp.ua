package ua.dp.primat.schedule.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.lesson.WeekType;

/**
 * Service which helps work with date or time extract information from it.
 * @author EniSh
 */
@Service
public class TimeService {

    public Semester currentSemester() {
        final Calendar c = Calendar.getInstance();
        if (c.get(Calendar.MONTH) > Calendar.AUGUST) {
            return new Semester(c.get(Calendar.YEAR), 1);
        } else {
            return new Semester(c.get(Calendar.YEAR) - 1, 2);
        }
    }

    public DayOfWeek todayDayOfWeek() {
        final Calendar c = Calendar.getInstance();
        final int dayNum = c.get(Calendar.DAY_OF_WEEK) - 2;
        if (dayNum < 0) {
            return DayOfWeek.SUNDAY;
        }
        return DayOfWeek.fromNumber(dayNum);
    }

    public DayOfWeek tomorrowDayOfWeek() {
        final Calendar c = Calendar.getInstance();
        final int dayNum = c.get(Calendar.DAY_OF_WEEK) - 1;
        return DayOfWeek.fromNumber(dayNum);
    }

    public WeekType weekTypeByDate(Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentWeekNum;
        currentWeekNum = c.get(Calendar.WEEK_OF_YEAR);
        if (c.get(Calendar.MONTH) < Calendar.SEPTEMBER) {
            c.add(Calendar.YEAR, -1);
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            c.set(Calendar.DATE, c.getMaximum(Calendar.DATE));
            currentWeekNum += c.get(Calendar.WEEK_OF_YEAR);
            final int dayInWeekCount = 7;
            if (c.get(Calendar.DAY_OF_WEEK) != ((c.getFirstDayOfWeek() + dayInWeekCount - 1) % dayInWeekCount)){
                currentWeekNum--;
            }
        }
        c.set(Calendar.MONTH, Calendar.SEPTEMBER);
        c.set(Calendar.DATE, 1);
        currentWeekNum -= c.get(Calendar.WEEK_OF_YEAR);

        return ((currentWeekNum) % 2 == 0) ? WeekType.NUMERATOR : WeekType.DENOMINATOR;
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
        final int educationDuration = 8;
        final ArrayList<Semester> semesters = new ArrayList<Semester>(educationDuration);

        for (int i = 1; i <= educationDuration; i++) {
            semesters.add(new Semester(group, i));
        }
        return semesters;
    }

    /**
     * returns number of semester for given group.
     * E.g. for PZ-08-1, number of semester 2009-2010 2 is 4.
     * @param group
     * @param semester
     * @return number of semester
     */
    public Long semesterNumberForGroup(StudentGroup group, Semester semester) {
        return 2 * (semester.getYear() - group.getYear()) + semester.getNumber();
    }
}
