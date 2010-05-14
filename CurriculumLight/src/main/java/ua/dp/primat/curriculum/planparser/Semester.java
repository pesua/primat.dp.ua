package ua.dp.primat.curriculum.planparser;

import ua.dp.primat.curriculum.data.FinalControlType;

/**
 *
 * @author Acer
 */
public class Semester {

    public FinalControlType fcType;
    public boolean isCourse;
    public WorkHours workHours;

    public Semester() {
        this.workHours = new WorkHours();
    }

    @Override
    public String toString() {
        return "[" + fcType.name() + "+" + Boolean.toString(isCourse) + " | " + workHours.toString() +  "]";
    }
}
