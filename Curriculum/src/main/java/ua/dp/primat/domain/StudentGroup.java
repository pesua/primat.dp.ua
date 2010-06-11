package ua.dp.primat.domain;

import java.io.Serializable;
import java.text.DecimalFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getGroups", query = "select n from StudentGroup n")
})
public class StudentGroup implements Serializable {
    public static final int CODE_LENGTH = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;

    @Column(length = CODE_LENGTH)
    private String code;

    private Long number;

    private Long year;
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "workloads_groups",
//        joinColumns =
//            @JoinColumn(name = "groupId"),
//        inverseJoinColumns =
//            @JoinColumn(name = "workloadId"))
//    private List<WorkloadOld> workloads = new ArrayList<WorkloadOld>();

    public StudentGroup() {
    }

    public StudentGroup(String code, Long number, Long year) {
        this.code = code;
        this.number = number;
        this.year = year;
    }

    public StudentGroup(String fullCode) {
        try {
            int firstDefis = fullCode.indexOf("-");
            int secondDefis = fullCode.indexOf("-", firstDefis + 1);
            this.code = fullCode.substring(0, firstDefis);
            this.year = 2000 + Long.parseLong(fullCode.substring(firstDefis + 1, secondDefis));
            this.number = Long.parseLong(fullCode.substring(secondDefis + 1));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

//    public List<WorkloadOld> getWorkloads() {
//        return workloads;
//    }
//
//    public void setWorkloads(List<WorkloadOld> workloads) {
//        this.workloads = workloads;
//    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    @Override
    public String toString() {
        final int yearMask = 100;
        DecimalFormat format = new DecimalFormat("00");
        String yearCode = format.format(getYear() % yearMask);
        return String.format("%s-%s-%d", getCode(), yearCode, getNumber());
    }
}
