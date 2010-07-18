package ua.dp.primat.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Entity for a custom lecturer.
 * @author fdevelop
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Lecturer.GET_ALL_LECTURERS, query = "from Lecturer"),
    @NamedQuery(name = Lecturer.GET_LECTURERS_BY_CATHEDRA, query = "from Lecturer where cathedra=:Cathedra")
})
public class Lecturer implements Serializable {

    public static final String GET_ALL_LECTURERS = "getAllLecrurers";
    public static final String GET_LECTURERS_BY_CATHEDRA = "getLecturersByCathedra";
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    private Cathedra cathedra;
    private LecturerType lecturerType;

    public Lecturer() {
    }

    public Lecturer(String name, Cathedra cathedra, LecturerType lecturerType) {
        this.name = name;
        this.cathedra = cathedra;
        this.lecturerType = lecturerType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Lecturer other = (Lecturer) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(id, other.id)
                .append(name, other.name)
                .append(cathedra, other.cathedra)
                .append(lecturerType, other.lecturerType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(INITODDNUMBER, MULTODDNUMBER)
                .append(id)
                .append(name)
                .append(cathedra)
                .append(lecturerType)
                .toHashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cathedra getCathedra() {
        return cathedra;
    }

    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
    }

    public LecturerType getLecturerType() {
        return lecturerType;
    }

    public void setLecturerType(LecturerType lecturerType) {
        this.lecturerType = lecturerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the short form of the person's name.
     * @return string like 'Surname A.B.'
     */
    public String getShortName() {
        try {
            final char wordSeparator = ' ';
            final String point = ".";
            final String short1 = name.charAt(name.indexOf(wordSeparator) + 1) + point;
            final String short2 = name.charAt(name.lastIndexOf(wordSeparator) + 1) + point;
            return name.substring(0, name.indexOf(wordSeparator)) + wordSeparator
                    + short1 + wordSeparator + short2;
        } catch (IndexOutOfBoundsException ie) {
            return name;
        }
    }

    @Override
    public String toString() {
        return getShortName();
    }
    private static final long serialVersionUID = 1L;
    private static final int INITODDNUMBER = 3;
    private static final int MULTODDNUMBER = 67;
}
