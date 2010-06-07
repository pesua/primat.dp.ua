package ua.dp.primat.schedule.view;

/**
 * Item for the 'number of lesson' + 'week position (numerator, denominator)',
 * which contains lesson's info (or NULL) for 7 days
 * @author fdevelop
 */
public class LessonQueryItem {

    private int pairnum;
    private Long weekpos;

    private Lesson monday;
    private Lesson tuesday;
    private Lesson wednesday;
    private Lesson thursday;
    private Lesson friday;
    private Lesson saturday;
    private Lesson sunday;

    public LessonQueryItem() {
    }

    public LessonQueryItem(int pairnum, Long weekpos) {
        this.pairnum = pairnum;
        this.weekpos = weekpos;
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

    public int getPairnum() {
        return pairnum;
    }

    public void setPairnum(int pairnum) {
        this.pairnum = pairnum;
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

    public Long getWeekpos() {
        return weekpos;
    }

    public void setWeekpos(Long weekpos) {
        this.weekpos = weekpos;
    }

    public Lesson getLessonForDay(int day) {
        switch (day) {
            case 1: return getMonday();
            case 2: return getTuesday();
            case 3: return getWednesday();
            case 4: return getThursday();
            case 5: return getFriday();
            case 6: return getSaturday();
            default: return getSunday();
        }
    }

    public void setLessonForDay(int day, Lesson lesson) {
        switch (day) {
            case 1: monday = lesson;
                break;
            case 2: tuesday = lesson;
                break;
            case 3: wednesday = lesson;
                break;
            case 4: thursday = lesson;
                break;
            case 5: friday = lesson;
                break;
            case 6: saturday = lesson;
                break;
            case 7: sunday = lesson;
                break;
        }
    }

}
