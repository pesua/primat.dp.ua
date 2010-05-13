/*
 *  
 */

package ua.dp.primat.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author EniSh
 */
@Entity
@Table(name="disciplines")
public class Discipline implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long disciplineId;

    @Column(name="name")
    String name;

    @Column(name="cathedra")
    @ManyToOne
    Cathedra cathedra;
    
    @OneToMany(mappedBy = "discepline")
    private List<Workload> workloads;
}
