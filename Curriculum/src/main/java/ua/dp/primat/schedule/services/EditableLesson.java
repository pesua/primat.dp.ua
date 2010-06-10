package ua.dp.primat.schedule.services;

import ua.dp.primat.curriculum.data.Discipline;
import ua.dp.primat.schedule.data.Lecturer;
import ua.dp.primat.schedule.data.Lesson;
import ua.dp.primat.schedule.data.Room;
import ua.dp.primat.schedule.data.WeekType;

/**
 * 
 * @author Administrator
 */
public class EditableLesson {

    public static EditableLesson FromLesson(Lesson lesson) {
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
    
    
    private Long id;
    private Discipline discipline;
    private Lecturer lecturer;
    private Lecturer asistant;
    private Room room;
    private WeekType weekType;

}
