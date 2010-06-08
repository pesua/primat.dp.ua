package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Entity for a custom lecturer
 * @author fdevelop
 */
@Entity
public class Lecturer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private Cathedra cathedra;

    public Lecturer() {
    }

    public Lecturer(String name, Cathedra cathedra) {
        this.name = name;
        this.cathedra = cathedra;
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

}
