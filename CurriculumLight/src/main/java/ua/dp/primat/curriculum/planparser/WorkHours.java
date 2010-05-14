package ua.dp.primat.curriculum.planparser;

/**
 *
 * @author fdevelop
 */
public class WorkHours {
    public double hLec;
    public double hPract;
    public double hLab;
    public double hInd;
    public double hSam;

    public double getSum() {
        return hLec+hPract+hLab+hInd+hSam;
    }

    @Override
    public String toString() {
        return "[" + hLec + "/" + hPract + "/" + hLab + "/" + hInd + "/" + hSam + "/" + "]";
    }
}
