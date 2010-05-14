/*
 * Every workload related to some groups
 */

package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author EniSh
 */
@Entity
public class StudentGroup implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="groupId")
    Long groupId;

    @Column(name="code", length=10)
    String code;

    @Column(name="number")
    Long number;

    @Column(name="group_year")
    Long year;

    @ManyToMany
    private List<WorkloadEntry> workloadentriess = new Vector<WorkloadEntry>();

    public StudentGroup() {
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

    public List<WorkloadEntry> getWorkloads() {
        return workloadentriess;
    }

    public void setWorkloads(List<WorkloadEntry> workloads) {
        this.workloadentriess = workloads;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }
}
