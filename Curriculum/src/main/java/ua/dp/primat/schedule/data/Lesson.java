package ua.dp.primat.schedule.data;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Lessons")
//some problem here
//@NamedQueries({
//    @NamedQuery(name="getLessons", query="select lesson from Lessons lesson join LessonDiscription ld where ld.studentGroup = :group")
//})
public class Lesson implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    Long id;

    @Column(name="lesson_number")
    Long lessonNumber;

    @Column(name="week_type")
    WeekType weekType;

    @Column(name="day_of_week")
    DayOfWeek dayOfWeek;

    @ManyToOne(cascade = CascadeType.ALL)
    Room room;

    @ManyToOne(cascade = CascadeType.ALL)
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
