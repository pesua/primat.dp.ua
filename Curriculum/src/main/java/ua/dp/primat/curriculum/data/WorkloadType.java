package ua.dp.primat.curriculum.data;

import java.util.Locale;
import java.util.ResourceBundle;

public enum WorkloadType {
    wtHumanities,
    wtNaturalScience,
    wtProfPract,
    wtProfPractStudent,
    wtProfPractUniver;

    @Override
    public String toString() {
        ResourceBundle localization = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
        switch(this) {
            case wtHumanities:
                return localization.getString("workloadType.Humanities");
            case wtNaturalScience:
                return localization.getString("workloadType.NaturalScience");
            case wtProfPract:
                return localization.getString("workloadType.ProfPract");
            case wtProfPractStudent:
                return localization.getString("workloadType.ProfPractStudent");
            default:
                return localization.getString("workloadType.ProfPractUniver");
        }
    }
}
