package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class IndividualControl implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    Long id;

    @Column(name="type", length=100)
    String type;

    @Column(name="week_num")
    Long weekNum;

    @ManyToOne(cascade = CascadeType.ALL)
    WorkloadEntry workloadEntry;

    public WorkloadEntry getWorkloadEntry() {
        return workloadEntry;
    }

    public void setWorkloadEntry(WorkloadEntry workloadEntry) {
        this.workloadEntry = workloadEntry;
    }

    public IndividualControl() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Long weekNum) {
        this.weekNum = weekNum;
    }
}
