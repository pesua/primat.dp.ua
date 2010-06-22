package ua.dp.primat.curriculum.planparser;

/**
 *
 * @author fdevelop
 */
public final class WorkHours {
    private double hoursLec;
    private double hoursPract;
    private double hoursLab;
    private double hoursInd;
    private double hoursSam;

    public double getHoursInd() {
        return hoursInd;
    }

    public void setHoursInd(double hoursInd) {
        this.hoursInd = hoursInd;
    }

    public double getHoursLab() {
        return hoursLab;
    }

    public void setHoursLab(double hoursLab) {
        this.hoursLab = hoursLab;
    }

    public double getHoursLec() {
        return hoursLec;
    }

    public void setHoursLec(double hoursLec) {
        this.hoursLec = hoursLec;
    }

    public double getHoursPract() {
        return hoursPract;
    }

    public void setHoursPract(double hoursPract) {
        this.hoursPract = hoursPract;
    }

    public double getHoursSam() {
        return hoursSam;
    }

    public void setHoursSam(double hoursSam) {
        this.hoursSam = hoursSam;
    }

    public double getSum() {
        return hoursLec+hoursPract+hoursLab+hoursInd+hoursSam;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(hoursLec);
        sb.append("/").append(hoursPract);
        sb.append("/").append(hoursLab);
        sb.append("/").append(hoursInd);
        sb.append("/").append(hoursSam);
        sb.append("]");
        return sb.toString();
    }
}
