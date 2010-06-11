package ua.dp.primat.schedule.services;

import ua.dp.primat.schedule.data.Lesson;
import ua.dp.primat.schedule.data.WeekType;

/**
 *
 * @author Administrator
 */
public class LessonItem {

    public void setOneLesson() {
        getNumerator().setWeekType(WeekType.BOTH);
    }
    
    public void setTwoLesson() {
        getNumerator().setWeekType(WeekType.NUMERATOR);
        getDenominator().setWeekType(WeekType.DENOMINATOR);
    }
    
    public boolean isOneLesson() {
        return getNumerator().getWeekType() == WeekType.BOTH;
    }

    public EditableLesson getDenominator() {
        return denominator;
    }

    public void setDenominator(EditableLesson denominator) {
        this.denominator = denominator;
    }

    public EditableLesson getNumerator() {
        return numerator;
    }

    public void setNumerator(EditableLesson numerator) {
        this.numerator = numerator;
    }

    public void mergeWithLesson(Lesson lesson) {
        if (lesson.getWeekType()==WeekType.DENOMINATOR) {
            denominator = EditableLesson.fromLesson(lesson);
        } else {
            numerator = EditableLesson.fromLesson(lesson);
        }
    }
    
    private EditableLesson numerator;
    private EditableLesson denominator;
}
