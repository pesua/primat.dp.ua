package ua.dp.primat.schedule.view.crosstab;

import java.io.Serializable;
import ua.dp.primat.schedule.data.DayOfWeek;
import ua.dp.primat.schedule.data.Lesson;
import ua.dp.primat.schedule.data.WeekType;

/**
 * Item for the 'number of lesson' + 'week position (numerator, denominator)',
 * which contains lesson's info (or NULL) for 7 days
 * @author fdevelop
 */
public class LessonQueryItem implements Serializable {

    private int lessonNumber;
    private WeekType weekType;

    private Lesson monday;
    private Lesson tuesday;
    private Lesson wednesday;
    private Lesson thursday;
    private Lesson friday;
    private Lesson saturday;
    private Lesson sunday;

    public LessonQueryItem() {
    }

    public LessonQueryItem(int pairnum, WeekType weekType) {
        this.lessonNumber = pairnum;
        this.weekType = weekType;
    }

    public Lesson getFriday() {
        return friday;
    }

    public void setFriday(Lesson friday) {
        this.friday = friday;
    }

    public Lesson getMonday() {
        return monday;
    }

    public void setMonday(Lesson monday) {
        this.monday = monday;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public Lesson getSaturday() {
        return saturday;
    }

    public void setSaturday(Lesson saturday) {
        this.saturday = saturday;
    }

    public Lesson getSunday() {
        return sunday;
    }

    public void setSunday(Lesson sunday) {
        this.sunday = sunday;
    }

    public Lesson getThursday() {
        return thursday;
    }

    public void setThursday(Lesson thursday) {
        this.thursday = thursday;
    }

    public Lesson getTuesday() {
        return tuesday;
    }

    public void setTuesday(Lesson tuesday) {
        this.tuesday = tuesday;
    }

    public Lesson getWednesday() {
        return wednesday;
    }

    public void setWednesday(Lesson wednesday) {
        this.wednesday = wednesday;
    }

    public WeekType getWeekType() {
        return weekType;
    }

    public void setWeekType(WeekType weekType) {
        this.weekType = weekType;
    }

    public Lesson getLessonForDay(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return getMonday();
            case TUESDAY: return getTuesday();
            case WEDNESDAY: return getWednesday();
            case THURSDAY: return getThursday();
            case FRIDAY: return getFriday();
            case SATURDAY: return getSaturday();
            default: return getSunday();
        }
    }

    public void setLessonForDay(DayOfWeek dayOfWeek, Lesson lesson) {
        switch (dayOfWeek) {
            case MONDAY: monday = lesson;
                break;
            case TUESDAY: tuesday = lesson;
                break;
            case WEDNESDAY: wednesday = lesson;
                break;
            case THURSDAY: thursday = lesson;
                break;
            case FRIDAY: friday = lesson;
                break;
            case SATURDAY: saturday = lesson;
                break;
            case SUNDAY: sunday = lesson;
                break;
        }
    }

}