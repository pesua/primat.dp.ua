package ua.dp.primat.curriculum.data;

import java.util.Locale;
import java.util.ResourceBundle;

public enum WeekType {
    NUMERATOR {

        @Override
        public String toString() {
            return BUNDLE.getString("weektype.numerator");
        }

    },
    DENOMINATOR {

        @Override
        public String toString() {
            return BUNDLE.getString("weektype.denominator");
        }

    },
    BOTH {

        @Override
        public String toString() {
            return BUNDLE.getString("weektype.both");
        }

    };

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
