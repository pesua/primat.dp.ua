package ua.dp.primat.schedule.view;

/**
 * TEMPORARY LESSON ENTITY. 
 * TODO: remove and replace with the real Entity from 'data' package,
 * when it will be available
 * @author fdevelop
 */
public class Lesson {

    private String subject_name;
    private Long type;
    private Long weekpos;
    private int day;
    private String lecturer;
    private Long pairnum;

    public Lesson(String subject_name, Long type, Long weekpos, int day, String lecturer, Long pairnum) {
        this.subject_name = subject_name;
        this.type = type;
        this.weekpos = weekpos;
        this.day = day;
        this.lecturer = lecturer;
        this.pairnum = pairnum;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public Long getPairnum() {
        return pairnum;
    }

    public void setPairnum(Long pairnum) {
        this.pairnum = pairnum;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getWeekpos() {
        return weekpos;
    }

    public void setWeekpos(Long weekpos) {
        this.weekpos = weekpos;
    }

}
