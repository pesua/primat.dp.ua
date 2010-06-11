package ua.dp.primat.domain.workload;

import java.util.Locale;
import java.util.ResourceBundle;

public enum FinalControlType {
    Exam,
    Setoff,
    DifferentiableSetoff,
    Nothing;

    @Override
    public String toString(){
        ResourceBundle localization = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
        switch(this) {
            case Exam:
                return localization.getString("finalControl.Exam");
            case DifferentiableSetoff:
                return localization.getString("finalControl.DiffSetoff");
            case Setoff:
                return localization.getString("finalControl.Setoff");
            default:
                return localization.getString("finalControl.Nothing");
        }
    }
}
