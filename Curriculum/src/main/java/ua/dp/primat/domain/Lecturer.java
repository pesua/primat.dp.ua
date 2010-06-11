package ua.dp.primat.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import ua.dp.primat.domain.Cathedra;

/**
 * Entity for a custom lecturer
 * @author fdevelop
 */
@Entity
@NamedQueries({
    @NamedQuery(name=Lecturer.GET_ALL_LECTURERS, query="from Lecturer"),
    @NamedQuery(name=Lecturer.GET_LECTURERS_BY_CATHEDRA, query="from Lecturer where cathedra=:Cathedra")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LecturerType getLecturerType() {
        return lecturerType;
    }

    public void setLecturerType(LecturerType lecturerType) {
        this.lecturerType = lecturerType;
    }

    public Cathedra getCathedra() {
        return cathedra;
    }

    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the short form of the person's name
     * @return string like 'Surname A.B.'
     */
    public String getShortName() {
        try {
            String short1 = name.charAt(name.indexOf(" ")+1) + ".";
            String short2 = name.charAt(name.lastIndexOf(" ")+1) + ".";
            return name.substring(0, name.indexOf(" ")) + " "
                + short1 + " " + short2;
        }
        catch (IndexOutOfBoundsException ie) {
            return name;
        }
    }

    @Override
    public String toString() {
        return getShortName();
    }

}
