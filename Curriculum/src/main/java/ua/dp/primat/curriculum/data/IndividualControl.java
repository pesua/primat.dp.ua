/*
 *  
 */

package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author EniSh
 */
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

    @ManyToOne
    WorkloadEntry we;

    public IndividualControl() {
    }

    public WorkloadEntry getWe() {
        return we;
    }

    public void setWe(WorkloadEntry we) {
        this.we = we;
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
