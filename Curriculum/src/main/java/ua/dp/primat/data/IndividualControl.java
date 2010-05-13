/*
 *  
 */

package ua.dp.primat.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author EniSh
 */
@Entity
@Table(name="individual_control")
public class IndividualControl implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    Long id;

    @Column(name="type", length=100)
    String type;

    @Column(name="week_num")
    Long weekNum;

    public IndividualControl() {
    }
}
