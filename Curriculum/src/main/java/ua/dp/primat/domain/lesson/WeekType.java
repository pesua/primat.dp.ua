package ua.dp.primat.domain.lesson;

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

    public static final int TYPECOUNT = 2;

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
