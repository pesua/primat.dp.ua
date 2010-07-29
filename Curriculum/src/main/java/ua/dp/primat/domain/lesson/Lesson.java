package ua.dp.primat.domain.lesson;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import ua.dp.primat.domain.Room;

@Entity
@NamedQueries({
    @NamedQuery(name = Lesson.GET_LESSONS_BY_GROUP_AND_DAY_QUERY, query = "select lesson from Lesson lesson join lesson.lessonDescription ld where ld.studentGroup = :group and ld.semester=:semester and dayOfWeek=:day and weekType=:weekType"),
    @NamedQuery(name = Lesson.GET_LESSONS_BY_GROUP_AND_SEMESTER_QUERY, query = "select lesson from Lesson lesson join lesson.lessonDescription ld where ld.studentGroup = :group and ld.semester=:semester"),
    @NamedQuery(name = Lesson.GET_LESSONS_BY_LECTURER_AND_SEMESTER_QUERY, query = "select lesson from Lesson lesson join lesson.lessonDescription ld  join ld.studentGroup sgroup where (ld.lecturer = :lecturer or ld.assistant=:lecturer) and 2*sgroup.year + ld.semester = 2*:year+:semester"),
    @NamedQuery(name = Lesson.GET_LESSONS_BY_ROOM_AND_SEMESTER_QUERY, query = "select lesson from Lesson lesson join lesson.lessonDescription ld  join ld.studentGroup sgroup where lesson.room = :room and 2*sgroup.year + ld.semester = 2*:year+:semester")
})
public class Lesson implements Serializable {

    public static final String GET_LESSONS_BY_GROUP_AND_DAY_QUERY = "getLessonsByGroupAndDay";
    public static final String GET_LESSONS_BY_GROUP_AND_SEMESTER_QUERY = "getLessonsBySemesterAndGroup";
    public static final String GET_LESSONS_BY_LECTURER_AND_SEMESTER_QUERY = "getLessonsBySemesterAndLecturer";
    public static final String GET_LESSONS_BY_ROOM_AND_SEMESTER_QUERY = "getLessonsBySemesterAndRoom";
    @Id
    @GeneratedValue
    private Long id;
    private Long lessonNumber;
    private WeekType weekType;
    private DayOfWeek dayOfWeek;
    @ManyToOne
    private Room room;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private LessonDescription lessonDescription;

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
    private static final long serialVersionUID = 1L;
}
