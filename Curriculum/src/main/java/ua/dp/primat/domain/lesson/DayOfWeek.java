package ua.dp.primat.domain.lesson;

import java.util.Locale;
import java.util.ResourceBundle;

public enum DayOfWeek {

    MONDAY(0, "day.monday"),
    TUESDAY(1, "day.tuesday"),
    WEDNESDAY(2, "day.wednesday"),
    THURSDAY(3, "day.thursday"),
    FRIDAY(4, "day.friday"),
    SATURDAY(5, "day.saturday"),
    SUNDAY(6, "day.sunday");

    /*
     * gets number of day. E.g. Monday is first day of week, method returns 0.
     * @return day's number of week (numeration from 0)
     */
    public int getNumber() {
        return num;
    }

    @Override
    public String toString() {
        return BUNDLE.getString(localizationKey);
    }

    public static DayOfWeek fromNumber(int num) {
        for (DayOfWeek d : values()) {
            if (num == d.num) {
                return d;
            }
        }
        return SUNDAY;
    }

    private DayOfWeek(int number, String localizationKey) {
        num = number;
        this.localizationKey = localizationKey;
    }

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
    private int num;
    private String localizationKey;
}
