package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="groups")
@NamedQueries({
    @NamedQuery(name="getGroups", query="select n from StudentGroup n")
})
public class StudentGroup implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="groupId")
    private Long groupId;

    @SuppressWarnings("MagicNumber")
    @Column(name="code", length=3)
    private String code;

    @Column(name="number")
    private Long number;

    @Column(name="group_year")
    private Long year;

    @ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    private List<Workload> workloads = new ArrayList<Workload>();

    public StudentGroup() {
    }

    public StudentGroup(String code, Long number, Long year) {
        this.code = code;
        this.number = number;
        this.year = year;
    }
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public List<Workload> getWorkloads() {
        return workloads;
    }

    public void setWorkloads(List<Workload> workloads) {
        this.workloads = workloads;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    @Override
    public String toString() {
        final int yearDigitCount = 2;
        
        return String.format(getCode() + "-%0$2d-%d", getYear()%100, getNumber());
        //getCode() + "-" + (yearPart.substring(yearPart.length() - yearDigitCount)) + "-" + getNumber();
    }
}
