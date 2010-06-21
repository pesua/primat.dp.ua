package ua.dp.primat.domain.lesson;

import java.util.Locale;
import java.util.ResourceBundle;

public enum DayOfWeek {

    MONDAY(0) {

        @Override
        public String toString() {
            return BUNDLE.getString("day.monday");
        }
    },
    TUESDAY(1) {

        @Override
        public String toString() {
            return BUNDLE.getString("day.tuesday");
        }
    },
    WEDNESDAY(2) {

        @Override
        public String toString() {
            return BUNDLE.getString("day.wednesday");
        }
    },
    THURSDAY(3) {

        @Override
        public String toString() {
            return BUNDLE.getString("day.thursday");
        }
    },
    FRIDAY(4) {

        @Override
        public String toString() {
            return BUNDLE.getString("day.friday");
        }
    },
    SATURDAY(5) {

        @Override
        public String toString() {
            return BUNDLE.getString("day.saturday");
        }
    },
    SUNDAY(6) {

        @Override
        public String toString() {
            return BUNDLE.getString("day.sunday");
        }
    };

    /*
     * gets number of day. E.g. Monday is first day of week, method returns 0.
     * @return day's number of week (numeration from 0)
     */
    public int getNumber() {
        return num;
    }

    public static DayOfWeek fromNumber(int num) {
        for (DayOfWeek d : values()) {
            if (num == d.num) {
                return d;
            }
        }
        return SUNDAY;
    }

    private DayOfWeek(int number) {
        num = number;
    }

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
    private int num;
}
