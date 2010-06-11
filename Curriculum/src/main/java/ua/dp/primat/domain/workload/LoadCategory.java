package ua.dp.primat.domain.workload;

import java.util.Locale;
import java.util.ResourceBundle;

public enum LoadCategory {
    Normative, Selective, AlternativeForWar;

    @Override
    public String toString() {
        ResourceBundle localization = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
        switch(this) {
            case Normative:
                return localization.getString("loadCategory.Normative");
            case Selective:
                return localization.getString("loadCategory.Selective");
            default:
                return localization.getString("loadCategory.AlternativeForWar");
        }
    }
}
