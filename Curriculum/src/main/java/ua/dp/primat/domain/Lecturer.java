package ua.dp.primat.domain;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
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
    @NamedQuery(name = Lecturer.GET_ALL_LECTURERS_QUERY, query = "select l from Lecturer l order by l.name"),
    @NamedQuery(name = Lecturer.GET_LECTURERS_BY_CATHEDRA_QUERY, query = "from Lecturer where cathedra=:Cathedra"),
    @NamedQuery(name = Lecturer.GET_LECRURER_BY_NAME_QUERY, query="select l from Lecturer l where l.name=:name")
})
public class Lecturer implements Serializable, Comparable<Lecturer> {

    public static final String GET_ALL_LECTURERS_QUERY = "getAllLecrurers";
    public static final String GET_LECTURERS_BY_CATHEDRA_QUERY = "getLecturersByCathedra";
    public static final String GET_LECRURER_BY_NAME_QUERY = "getLecturerByName";
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

    public int compareTo(Lecturer o) {
        String name1 = name.toLowerCase();
        String name2 = o.getName().toLowerCase();
        ResourceBundle bundle = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
        String alphabet  =  bundle.getString("alphabet");
        for (int i = 0; i < Math.min(name1.length(), name2.length()); i++) {
            if (alphabet.indexOf(name1.charAt(i)) == alphabet.indexOf(name2.charAt(i))) {
                continue;
            }
            if (alphabet.indexOf(name1.charAt(i)) > alphabet.indexOf(name2.charAt(i))) {
                return 1;
            } else {
                return -1;
            }
        }
        return (name1.length() < name2.length()) ? -1 : 1;
    }
}
