package ua.dp.primat.domain.workload;

import java.util.Locale;
import java.util.ResourceBundle;

public enum LoadCategory {

    Normative {

        @Override
        public String toString() {
            return LOCALIZATION.getString("loadCategory.Normative");
        }
    },
    Selective {

        @Override
        public String toString() {
            return LOCALIZATION.getString("loadCategory.Selective");
        }
    },
    AlternativeForWar {

        @Override
        public String toString() {
            return LOCALIZATION.getString("loadCategory.AlternativeForWar");
        }
    };

    private static final ResourceBundle LOCALIZATION = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
