package ua.dp.primat.curriculum.planparser;

/**
 *
 * @author fdevelop
 */
public final class IndividualControlEntry {

    private int semester;
    private String type;
    private int weekNum;

    public IndividualControlEntry() {
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

}
