package ua.dp.primat.curriculum.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(@NamedQuery(name=Room.GET_ALL_ROOMS_QUERY, query="from Room"))
public class Room implements Serializable {
    public static final String GET_ALL_ROOMS_QUERY = "getAllRooms";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;
    Long building;
    Long number;

    public Room() {
    }

    public Room(Long building, Long number) {
        this.building = building;
        this.number = number;
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
}