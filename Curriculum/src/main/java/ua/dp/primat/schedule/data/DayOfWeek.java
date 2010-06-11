package ua.dp.primat.schedule.data;

import java.util.Locale;
import java.util.ResourceBundle;

public enum DayOfWeek {
    MONDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.monday");
        }

        @Override
        public int getNumber() {
            return 0;
        }
    },
    TUESDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.tuesday");
        }

        @Override
        public int getNumber() {
            return 1;
        }
    },
    WEDNESDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.wednesday");
        }

        @Override
        public int getNumber() {
            return 2;
        }
    },
    THURSDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.thursday");
        }

        @Override
        public int getNumber() {
            return 3;
        }
    },
    FRIDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.friday");
        }

        @Override
        public int getNumber() {
            return 4;
        }
    },
    SATURDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.saturday");
        }

        @Override
        public int getNumber() {
            return 5;
        }
    },
    SUNDAY {

        @Override
        public String toString() {
            return BUNDLE.getString("day.sunday");
        }

        @Override
        public int getNumber() {
            return 6;
        }
    };

    /*
     * gets number of day. E.g. Monday is first day of week, method returns 0.
     * @return day's number of week (numeration from 0)
     */
    public abstract int getNumber();
    public static DayOfWeek fromNumber(int num) {
        switch(num) {
            case 0: return MONDAY;
            case 1: return TUESDAY;
            case 2: return WEDNESDAY;
            case 3: return THURSDAY;
            case 4: return FRIDAY;
            case 5: return SATURDAY;
            default: return SUNDAY;
        }
    }

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
