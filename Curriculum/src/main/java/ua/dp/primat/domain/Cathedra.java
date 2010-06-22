package ua.dp.primat.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Cathedra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    public Cathedra() {
    }

    public Cathedra(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long cathedraId) {
        this.id = cathedraId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
