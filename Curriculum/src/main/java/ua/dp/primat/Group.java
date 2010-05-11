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
public class Group {
    String code;
    Long number;
    Long year;
    Set workloads = new HashSet();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Set getWorkloads() {
        return workloads;
    }

    public void setWorkloads(Set workloads) {
        this.workloads = workloads;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

}
