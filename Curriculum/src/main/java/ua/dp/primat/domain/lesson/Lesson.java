package ua.dp.primat.domain.lesson;

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
import ua.dp.primat.domain.Room;

@Entity
@NamedQueries({
    @NamedQuery(name=Lesson.GET_LESSONS_BY_GROUP_QUERY, query="select lesson from Lesson lesson join lesson.lessonDescription ld where ld.studentGroup = :group"),
    @NamedQuery(name=Lesson.GET_LESSONS_BY_GROUP_AND_SEMESTER_QUERY, query="select lesson from Lesson lesson join lesson.lessonDescription ld where ld.studentGroup = :group and ld.semester=:semester")
})
public class Lesson implements Serializable {
    public static final String GET_LESSONS_BY_GROUP_QUERY = "getLessons";
    public static final String GET_LESSONS_BY_GROUP_AND_SEMESTER_QUERY = "getLessonsBySemesterAndGroup";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;

    Long lessonNumber;

    WeekType weekType;

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
