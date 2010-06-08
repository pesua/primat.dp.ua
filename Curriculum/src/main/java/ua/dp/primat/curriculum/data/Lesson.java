package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Lesson implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;

    Long lessonNumber;

    WeekType weekType;

    DayOfWeek dayOfWeek;

    @ManyToOne
    Room room;

    @ManyToOne
    LessonDescription lessonDescription;

    public Lesson() {
    }

    public Lesson(Long lessonNumber, WeekType weekType, DayOfWeek dayOfWeek, Room room, LessonDescription lessonDescription) {
        this.lessonNumber = lessonNumber;
        this.weekType = weekType;
        this.dayOfWeek = dayOfWeek;
        this.room = room;
        this.lessonDescription = lessonDescription;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(Long lessonNumber) {
        this.lessonNumber = lessonNumber;
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

    public LessonDescription getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(LessonDescription lessonDescription) {
        this.lessonDescription = lessonDescription;
    }
}
