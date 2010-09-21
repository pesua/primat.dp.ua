package ua.dp.primat.domain.workload;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import ua.dp.primat.domain.Cathedra;

/**
 * Represent academic discipline in university.
 * @author EniSh
 */
@Entity
@NamedQueries({
    @NamedQuery(name=Discipline.GET_ALL_DISCIPLINES_QUERY, query="select n from Discipline n order by n.name")
})
public class Discipline implements Serializable {
    public static final String GET_ALL_DISCIPLINES_QUERY = "getAllDisciplines";
    private static final long serialVersionUID = 1L;
    
    public Discipline() {
    }

    public Discipline(String name, Cathedra cathedra) {
        this.name = name;
        this.cathedra = cathedra;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Discipline other = (Discipline) obj;
        return new EqualsBuilder()
                .append(name, other.name)
                .append(cathedra, other.cathedra)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).hashCode();
    }

    @Override
    public String toString() {
        return name + " (" + cathedra + ")";
    }

    public Cathedra getCathedra() {
        return cathedra;
    }

    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long disciplineId) {
        this.id = disciplineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Cathedra cathedra;
}
