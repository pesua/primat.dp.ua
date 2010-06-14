package ua.dp.primat.schedule.services;

import java.io.Serializable;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.domain.lesson.DayOfWeek;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.lesson.Lesson;
import ua.dp.primat.domain.lesson.LessonDescription;
import ua.dp.primat.domain.Room;
import ua.dp.primat.domain.lesson.WeekType;

/**
 * 
 * @author Administrator
 */
public class EditableLesson implements Serializable {
    private static final long serialVersionUID = 1L;

    public static EditableLesson fromLesson(Lesson lesson) {
        EditableLesson el = new EditableLesson();

        if (lesson != null) {
            el.setId(lesson.getId());
            el.setRoom(lesson.getRoom());
            el.setWeekType(lesson.getWeekType());
            el.setLecturer(lesson.getLessonDescription().getLecturer());
            el.setAsistant(lesson.getLessonDescription().getAssistant());
            el.setDiscipline(lesson.getLessonDescription().getDiscipline());
        }

        return el;
    }

    public EditableLesson() {
    }

    public Lecturer getAsistant() {
        return asistant;
    }

    public void setAsistant(Lecturer asistant) {
        this.asistant = asistant;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public WeekType getWeekType() {
        return weekType;
    }

    public void setWeekType(WeekType weekType) {
        this.weekType = weekType;
    }

    public Lesson toLesson(DayOfWeek day, Long lessonNum) {
        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setRoom(room);
        lesson.setWeekType(weekType);
        lesson.setDayOfWeek(day);
        lesson.setLessonNumber(lessonNum);

        LessonDescription description = new LessonDescription();
        description.setAssistant(asistant);
        description.setLecturer(lecturer);
        description.setDiscipline(discipline);
        //description.set

        lesson.setLessonDescription(description);
        return lesson;
    }
    
    
    private Long id;
    private Discipline discipline;
    private Lecturer lecturer;
    private Lecturer asistant;
    private Room room;
    private WeekType weekType;

}
