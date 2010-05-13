/*
 * Every workload related to some groups
 */

package ua.dp.primat.data;

import java.io.Serializable;
import java.util.List;
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
@Table(name="groups")
public class StudentGroup implements Serializable {
    @ManyToMany(mappedBy = "groups")
    private List<Workload> workloads;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    Long groupId;

    @Column(name="code", length=3)
    String code;

    @Column(name="number")
    Long number;

    @Column(name="group_year")
    Long year;
}
