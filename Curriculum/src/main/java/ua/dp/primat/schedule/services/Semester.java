package ua.dp.primat.schedule.services;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import ua.dp.primat.domain.StudentGroup;

/**
 *
 * @author EniSh
 */
public class Semester {

    public Semester(long year, long number) {
        this.year = year;
        this.number = number;
    }

    public Semester(StudentGroup group, long number) {
        this.year = group.getYear() + (number - 1) / 2;
        this.number = (number - 1) % 2 + 1;
    }

    @Override
    public String toString() {
        return year + "-" + (year+1) + ", " + number + " semester";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Semester other = (Semester) obj;
        return new EqualsBuilder()
                .append(this.number, other.number)
                .append(this.year, other.year)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(year).append(number).toHashCode();
    }

    public long getNumber() {
        return number;
    }

    public long getYear() {
        return year;
    }

    private long year;
    private long number;
}
