/*
 *  
 */

package ua.dp.primat;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author EniSh
 */
public class Cathedra {
    Long cathedraId;
    String name;
    Set disceplines = new HashSet();

    public Long getCathedraId() {
        return cathedraId;
    }

    public void setCathedraId(Long cathedraId) {
        this.cathedraId = cathedraId;
    }

    public Set getDisceplines() {
        return disceplines;
    }

    public void setDisceplines(Set disceplines) {
        this.disceplines = disceplines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
