package ua.dp.primat.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(
@NamedQuery(name = Room.GET_ALL_ROOMS_QUERY, query = "from Room"))
public class Room implements Serializable {

    public static final String GET_ALL_ROOMS_QUERY = "getAllRooms";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    private Long building;
    private Long number;

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
        if (this.id == null) {
            return (this.getNumber() == other.getNumber()) && (this.getBuilding() == other.getBuilding());
        }
        if (this.id != other.id && !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash;
        if (this.id != null) { hash += this.id.hashCode(); }
        return hash;
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
}
