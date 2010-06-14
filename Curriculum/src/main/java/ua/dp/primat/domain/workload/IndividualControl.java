package ua.dp.primat.domain.workload;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * Represent individual control of every student at some lesson. E.g. workload "history"
 * can has a control work at second week and coloqium at third week
 */
@Entity
public class IndividualControl implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    private Long weekNum;

    public IndividualControl() {
    }

    public IndividualControl(String type, Long weekNum) {
        this.type = type;
        this.weekNum = weekNum;
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
