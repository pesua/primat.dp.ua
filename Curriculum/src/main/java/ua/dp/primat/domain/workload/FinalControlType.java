package ua.dp.primat.domain.workload;

import java.util.Locale;
import java.util.ResourceBundle;

public enum FinalControlType {
    Exam {
        @Override
        public String toString() {
            return localization.getString("finalControl.Exam");
        }
    },
    Setoff {
        @Override
        public String toString() {
            return localization.getString("finalControl.Setoff");
        }
    },
    DifferentiableSetoff {
        @Override
        public String toString() {
            return localization.getString("finalControl.DiffSetoff");
        }
    },
    Nothing {
        @Override
        public String toString() {
            return localization.getString("finalControl.Nothing");
        }
    };
    public static final ResourceBundle localization = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
