package ua.dp.primat.domain.workload;

import java.util.Locale;
import java.util.ResourceBundle;

public enum FinalControlType {
    Exam {
        @Override
        public String toString() {
            return LOCALIZATION.getString("finalControl.Exam");
        }
    },
    Setoff {
        @Override
        public String toString() {
            return LOCALIZATION.getString("finalControl.Setoff");
        }
    },
    DifferentiableSetoff {
        @Override
        public String toString() {
            return LOCALIZATION.getString("finalControl.DiffSetoff");
        }
    },
    Nothing {
        @Override
        public String toString() {
            return LOCALIZATION.getString("finalControl.Nothing");
        }
    };

    private static final ResourceBundle LOCALIZATION = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
