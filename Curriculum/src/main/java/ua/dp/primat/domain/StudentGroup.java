package ua.dp.primat.domain;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@NamedQueries({
    @NamedQuery(name = StudentGroup.GET_GROUPS_QUERY, query = "select n from StudentGroup n order by n.code, n.year, n.number"),
    @NamedQuery(name = StudentGroup.GET_GROUPS_BY_CODE_AND_YEAR_AND_NUMBER_QUERY, query = "select n from StudentGroup n where n.code = :code and n.year = :year and n.number = :number")
})
public class StudentGroup implements Serializable {

    public static final String GET_GROUPS_QUERY = "getGroups";
    public static final String GET_GROUPS_BY_CODE_AND_YEAR_AND_NUMBER_QUERY = "getGroupsByCodeAndYearAndNumber";
    public static final int CODE_LENGTH = 2;

    public StudentGroup() {
    }

    public StudentGroup(String code, Long number, Long year) {
        this.code = code;
        this.number = number;
        this.year = year;
    }

    public StudentGroup(String fullCode) {
        if (!Pattern.matches("\\D{2,5}-\\d{2,4}-\\d{1,}", fullCode)) {
            throw new IllegalArgumentException("Wrong student group code");
        }

        try {
            final char separator = '-';
            final int firstDefis = fullCode.indexOf(separator);
            final int secondDefis = fullCode.indexOf(separator, firstDefis + 1);
            this.code = fullCode.substring(0, firstDefis);
            this.year = YEARBASE + Long.parseLong(fullCode.substring(firstDefis + 1, secondDefis));
            this.number = Long.parseLong(fullCode.substring(secondDefis + 1));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long groupId) {
        this.id = groupId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StudentGroup other = (StudentGroup) obj;
        return new EqualsBuilder()
                .append(code, other.code)
                .append(year, other.year)
                .append(number, other.number)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(code)
                .append(year)
                .append(number)
                .hashCode();
    }

    @Override
    public String toString() {
        final int yearMask = 100;
        final DecimalFormat format = new DecimalFormat("00");
        final String yearCode = format.format(getYear() % yearMask);
        return String.format("%s-%s-%d", getCode(), yearCode, getNumber());
    }
    private static final Long YEARBASE = 2000L;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = CODE_LENGTH)
    private String code;
    private Long number;
    private Long year;
}
