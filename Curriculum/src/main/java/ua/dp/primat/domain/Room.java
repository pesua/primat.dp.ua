package ua.dp.primat.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@NamedQueries(
@NamedQuery(name = Room.GET_ALL_ROOMS_QUERY, query = "from Room"))
public class Room implements Serializable {

    public static final String GET_ALL_ROOMS_QUERY = "getAllRooms";

    public Room() {
    }

    public Room(Long building, Long number) {
        this.building = building;
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(id, other.id)
                .append(getNumber(), other.getNumber())
                .append(getBuilding(), other.getBuilding())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(INITODDNUMBER, MULTODDNUMBER)
                .append(id)
                .toHashCode();
    }

    public Long getBuilding() {
        return building;
    }

    public void setBuilding(Long building) {
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return building + "/" + number;
    }

    private static final int INITODDNUMBER = 7;
    private static final int MULTODDNUMBER = 79;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    private Long building;
    private Long number;
}
