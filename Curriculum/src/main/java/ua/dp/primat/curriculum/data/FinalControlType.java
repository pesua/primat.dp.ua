package ua.dp.primat.curriculum.data;

import java.util.Locale;
import java.util.ResourceBundle;

public enum FinalControlType {
    Exam,
    Setoff,
    DifferentiableSetoff,
    Nothing;

    @Override
    public String toString(){
        ResourceBundle localization = ResourceBundle.getBundle("FinalControlType", new Locale("uk"));
        switch(this) {
            case Exam:
                return localization.getString("Exam");
            case DifferentiableSetoff:
                return localization.getString("DiffSetoff");
            case Setoff:
                return localization.getString("Setoff");
            default:
                return localization.getString("Nothing");
        }
    }
}
