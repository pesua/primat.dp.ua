package ua.dp.primat.domain.workload;

import java.util.Locale;
import java.util.ResourceBundle;

public enum LoadCategory {

    Normative {

        @Override
        public String toString() {
            return localization.getString("loadCategory.Normative");
        }
    },
    Selective {

        @Override
        public String toString() {
            return localization.getString("loadCategory.Selective");
        }
    },
    AlternativeForWar {

        @Override
        public String toString() {
            return localization.getString("loadCategory.AlternativeForWar");
        }
    };
    public static final ResourceBundle localization = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
