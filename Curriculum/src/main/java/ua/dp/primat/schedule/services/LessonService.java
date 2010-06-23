package ua.dp.primat.schedule.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.primat.domain.Cathedra;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.LecturerType;
import ua.dp.primat.domain.Room;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.domain.lesson.*;
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.repositories.DisciplineRepository;
import ua.dp.primat.repositories.LecturerRepository;
import ua.dp.primat.repositories.LessonDescriptionRepository;
import ua.dp.primat.repositories.LessonRepository;
import ua.dp.primat.repositories.RoomRepository;

/**
 * Service for operations on objects Lesson, LessonDescription, Lecturer,
 * Room.
 * @author fdevelop
 */
@Service
@Transactional
public class LessonService {

    public void saveLesson(Lesson lesson) {
        Long id = lesson.getLessonDescription().getId();
        LessonDescription descr = (id != null) ? lessonDescriptionRepository.find(id) : null;
        if (descr != null) {
            lesson.setLessonDescription(descr);
        } else {
            lessonDescriptionRepository.store(lesson.getLessonDescription());
        }
        lessonRepository.store(lesson);
    }

    public void deleteLesson(Long id) {
        Lesson lesson = lessonRepository.find(id);
        lessonRepository.remove(lesson);
    }

    public void saveLessonDescription(LessonDescription lessonDescription) {
        lessonDescriptionRepository.store(lessonDescription);
    }

    public void deleteLessonDescription(LessonDescription lessonDescription) {
        lessonDescriptionRepository.remove(lessonDescription);
    }

    public void saveRoom(Room room) {
        roomRepository.store(room);
    }

    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }

    public void saveLecturer(Lecturer lecturer) {
        lecturerRepository.store(lecturer);
    }

    public void deleteLecturer(Lecturer lecturer) {
        lecturerRepository.delete(lecturer);
    }

    public void saveDiscipline(Discipline discipline) {
        disciplineRepository.store(discipline);
    }

    public void deleteDiscipline(Discipline discipline) {
        disciplineRepository.delete(discipline);
    }

    public List<Lesson> getLessonsForGroupAndSemester(StudentGroup studentGroup,
            Long semester) {
        return lessonRepository.getLessonsByGroupAndSemester(studentGroup, semester);
    }

    /**
     * Query, that returns extacly 6 lessons for the specified DayOfWeek and WeekType.
     * @param listLesson - data to search in
     * @param day
     * @param week
     * @return the list of Lesson, which are accepted by parameters
     */
    public List<Lesson> getLessonsPerDay(List<Lesson> listLesson, DayOfWeek day, WeekType week) {
        final Lesson[] dayLessons = new Lesson[6];

        //fill an array with empty items
        for (int i = 0; i < dayLessons.length; i++) {
            dayLessons[i] = new Lesson(Long.valueOf(i+1), WeekType.BOTH, day, null, null);
        }

        //if no data - leave the method
        if (listLesson == null) {
            return Arrays.asList(dayLessons);
        }

        for (Lesson l : listLesson) {
            if ((l.getDayOfWeek() == day)
                    && ((l.getWeekType() == week)
                    || (l.getWeekType() == WeekType.BOTH))) {
                int lessonNumber = l.getLessonNumber().intValue();
                dayLessons[lessonNumber-1] = l;
            }
        }

        return Arrays.asList(dayLessons);
    }
    
    /**
     * TODO: fake data
     * @param studentGroup
     * @param semester
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Lesson> getLessons(StudentGroup studentGroup, Long semester) {
        final List<Lesson> list  = new ArrayList<Lesson>();

        final Cathedra cathedra = new Cathedra();
        cathedra.setName("Math.EOM");

        final Room room46 = new Room(Long.valueOf(3), Long.valueOf(46));
        final Room room45 = new Room(Long.valueOf(3), Long.valueOf(45));
        final Room room31 = new Room(Long.valueOf(3), Long.valueOf(31));
        final Room room22 = new Room(Long.valueOf(2), Long.valueOf(25));

        final Discipline d1 = new Discipline("Databases", cathedra);
        final Discipline d2 = new Discipline("Assembler", cathedra);
        final Discipline d3 = new Discipline("Arch. EOM.", cathedra);
        final Discipline d4 = new Discipline("K.I.T.", cathedra);
        final Discipline d5 = new Discipline("Physical culture", cathedra);
        final Discipline d6 = new Discipline("Mathematic analyze", cathedra);

        final Lecturer teacher1 = new Lecturer("Mashenko L. V.", cathedra, LecturerType.SENIORLECTURER);
        final Lecturer teacher2 = new Lecturer("Efimov V. N.", cathedra, LecturerType.SENIORLECTURER);
        final Lecturer teacher3 = new Lecturer("Bulana T. M.", cathedra, LecturerType.ASSIATANT);
        final Lecturer teacher4 = new Lecturer("Archangelska U. M.", cathedra, LecturerType.ASSIATANT);
        final Lecturer teacher5 = new Lecturer("Segeda N. E.", cathedra, LecturerType.SENIORLECTURER);
        final Lecturer teacher6 = new Lecturer("Kuznecov K. G.", cathedra, LecturerType.DOCENT);
        final Lecturer teacher7 = new Lecturer("Zayceva V. N.", cathedra, LecturerType.SENIORLECTURER);
        final Lecturer teacher8 = new Lecturer("Tonkoshkur I. S.", cathedra, LecturerType.DOCENT);
        final Lecturer teacher9 = new Lecturer("Emeliyanenko T. G.", cathedra, LecturerType.DOCENT);

        final LessonDescription ld1 = new LessonDescription(d1, studentGroup, Long.valueOf(4), LessonType.LECTURE, teacher1, null);
        final LessonDescription ld2 = new LessonDescription(d2, studentGroup, Long.valueOf(4), LessonType.LECTURE, teacher9, null);
        final LessonDescription ld3 = new LessonDescription(d2, studentGroup, Long.valueOf(4), LessonType.LABORATORY, teacher4, teacher5);
        final LessonDescription ld4 = new LessonDescription(d3, studentGroup, Long.valueOf(4), LessonType.LABORATORY, teacher3, teacher4);
        final LessonDescription ld5 = new LessonDescription(d4, studentGroup, Long.valueOf(4), LessonType.LABORATORY, teacher6, teacher3);
        final LessonDescription ld6 = new LessonDescription(d4, studentGroup, Long.valueOf(4), LessonType.LECTURE, teacher6, null);
        final LessonDescription ld7 = new LessonDescription(d5, studentGroup, Long.valueOf(4), LessonType.PRACTICE, teacher7, null);
        final LessonDescription ld8 = new LessonDescription(d6, studentGroup, Long.valueOf(4), LessonType.LECTURE, teacher8, null);
        final LessonDescription ld9 = new LessonDescription(d6, studentGroup, Long.valueOf(4), LessonType.PRACTICE, teacher8, null);
        final LessonDescription ld0 = new LessonDescription(d3, studentGroup, Long.valueOf(4), LessonType.LECTURE, teacher2, null);

        if (semester == 4) {
            list.add(new Lesson(Long.valueOf(2), WeekType.BOTH, DayOfWeek.THURSDAY, room45, ld5));
            list.add(new Lesson(Long.valueOf(2), WeekType.BOTH, DayOfWeek.MONDAY, room22, ld7));
            list.add(new Lesson(Long.valueOf(2), WeekType.BOTH, DayOfWeek.FRIDAY, room22, ld7));
            list.add(new Lesson(Long.valueOf(2), WeekType.BOTH, DayOfWeek.TUESDAY, room46, ld8));
            list.add(new Lesson(Long.valueOf(3), WeekType.BOTH, DayOfWeek.MONDAY, room31, ld1));
            list.add(new Lesson(Long.valueOf(3), WeekType.BOTH, DayOfWeek.TUESDAY, room31, ld2));
            list.add(new Lesson(Long.valueOf(3), WeekType.BOTH, DayOfWeek.FRIDAY, room46, ld6));
            list.add(new Lesson(Long.valueOf(4), WeekType.NUMERATOR, DayOfWeek.THURSDAY, room45, ld3));
            list.add(new Lesson(Long.valueOf(4), WeekType.BOTH, DayOfWeek.WEDNESDAY, room45, ld4));
            list.add(new Lesson(Long.valueOf(4), WeekType.BOTH, DayOfWeek.MONDAY, room46, ld0));
            list.add(new Lesson(Long.valueOf(5), WeekType.DENOMINATOR, DayOfWeek.MONDAY, room46, ld9));
        }

        return list;
    }

    @Resource
    private LessonRepository lessonRepository;

    @Resource
    private LessonDescriptionRepository lessonDescriptionRepository;

    @Resource
    private DisciplineRepository disciplineRepository;

    @Resource
    private RoomRepository roomRepository;

    @Resource
    private LecturerRepository lecturerRepository;
}
