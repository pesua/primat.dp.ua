package ua.dp.primat.schedule.services;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.NotImplementedException;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.StudentGroup;

/**
 *
 * @author EniSh
 */
public class LiferayUserService {

    public boolean isLecturerPage(HttpServletRequest req) {
        //TODO
        throw new NotImplementedException();
    }

    public boolean isStudentPage(HttpServletRequest req) {
        //TODO
        throw new NotImplementedException();
    }

    public boolean isStudentGroupPage(HttpServletRequest req) {
        //TODO
        throw new NotImplementedException();
    }

    /**
     * returns student group which correspond owner of site. owner can be user or community
     * of users(student group)
     * @param req
     * @return
     * @throws IllegalArgumentException if page owner isn't student or student group
     */
    public StudentGroup studentGroupFrom(HttpServletRequest req) throws IllegalArgumentException {
        //TODO
        throw new NotImplementedException();
    }

    /**
     * return lecturer which correspond owner of site.
     * @param req
     * @return
     * @throws IllegalArgumentException if page owner isn't lecturer
     */
    public Lecturer lecturerFrom(HttpServletRequest req) throws IllegalArgumentException {
        //TODO
        throw new NotImplementedException();
    }
}
