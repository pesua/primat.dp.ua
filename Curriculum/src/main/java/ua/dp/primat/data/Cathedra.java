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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author EniSh
 */
@Entity
@Table(name = "Cathedra")
public class Cathedra implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "cathedra_id")
    Long cathedraId;

    @Column(name = "name", length = 255)
    String name;
    
    @OneToMany(mappedBy = "cathedra")
    private List<Discipline> disciplines;

    public Cathedra() {
    }
}
