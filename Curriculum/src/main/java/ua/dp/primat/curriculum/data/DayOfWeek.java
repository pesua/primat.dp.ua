package ua.dp.primat.curriculum.data;

import java.util.Locale;
import java.util.ResourceBundle;

public enum DayOfWeek {
    MONDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.monday");
        }

    },
    TUESDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.tuesday");
        }

    },
    WEDNESDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.wednesday");
        }

    },
    THURSDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.thursday");
        }

    },
    FRIDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.friday");
        }

    },
    SATURDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.saturday");
        }

    },
    SUNDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.sunday");
        }

    };

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
