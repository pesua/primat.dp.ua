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
        final StringBuilder sb = new StringBuilder();
        final String sl = new String("/");
        sb.append("[").append(hoursLec);
        sb.append(sl).append(hoursPract);
        sb.append(sl).append(hoursLab);
        sb.append(sl).append(hoursInd);
        sb.append(sl).append(hoursSam);
        sb.append("]");
        return sb.toString();
    }
}
