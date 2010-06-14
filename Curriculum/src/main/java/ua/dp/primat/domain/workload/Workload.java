package ua.dp.primat.domain.workload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import ua.dp.primat.domain.StudentGroup;

/**
 *
 * @author EniSh
 */
@Entity
@NamedQueries({
    @NamedQuery(name=Workload.GET_WORKLOAD_BY_GROUP_AND_SEMESTER,
                query="select load from Workload load where load.studentGroup=:group and load.semesterNumber=:semester")
})
public class Workload implements Serializable {
    public static final String GET_WORKLOAD_BY_GROUP_AND_SEMESTER = "getWowkloadByGroupAndSemester";
    private static final long serialVersionUID = 1L;

    public Workload() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCourseWork() {
        return courseWork;
    }

    public void setCourseWork(Boolean couseWork) {
        this.courseWork = couseWork;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public FinalControlType getFinalControlType() {
        return finalControlType;
    }

    public void setFinalControlType(FinalControlType finalControlType) {
        this.finalControlType = finalControlType;
    }

    public List<IndividualControl> getIndividualControl() {
        return individualControl;
    }

    public void setIndividualControl(List<IndividualControl> individualControl) {
        this.individualControl = individualControl;
    }

    public Long getLaboratoryHours() {
        return laboratoryHours;
    }

    public void setLaboratoryHours(Long laboratoryHours) {
        this.laboratoryHours = laboratoryHours;
    }

    public Long getLectionHours() {
        return lectionHours;
    }

    public void setLectionHours(Long lectionHours) {
        this.lectionHours = lectionHours;
    }

    public LoadCategory getLoadCategory() {
        return loadCategory;
    }

    public void setLoadCategory(LoadCategory loadCategory) {
        this.loadCategory = loadCategory;
    }

    public Long getPracticeHours() {
        return practiceHours;
    }

    public void setPracticeHours(Long practiceHours) {
        this.practiceHours = practiceHours;
    }

    public Long getSelfworkHours() {
        return selfworkHours;
    }

    public void setSelfworkHours(Long selfworkHours) {
        this.selfworkHours = selfworkHours;
    }

    public Long getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(Long semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public WorkloadType getType() {
        return type;
    }

    public void setType(WorkloadType type) {
        this.type = type;
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade=CascadeType.ALL)
    private Discipline discipline;

    private WorkloadType type;

    private LoadCategory loadCategory;

    @ManyToOne
    private StudentGroup studentGroup;

    private Long semesterNumber;

    private Long lectionHours;

    private Long laboratoryHours;

    private Long practiceHours;

    private Long selfworkHours;

    private FinalControlType finalControlType;

    private Boolean courseWork;

    @OneToMany(cascade=CascadeType.ALL)
    private List<IndividualControl> individualControl = new ArrayList<IndividualControl>();
}
