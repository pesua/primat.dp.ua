package ua.dp.primat.schedule.services;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.repositories.StudentGroupRepository;

/**
 *
 * @author EniSh
 */
@Service
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
        try {
            final ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
            Group group = GroupLocalServiceUtil.getGroup(themeDisplay.getScopeGroupId());
            if (group.isUser()) {
                User user = UserLocalServiceUtil.getUserById(group.getClassPK());
                List<Group> userGroups = user.getGroups();

                StudentGroup studentGroup = null;
                for (Group g : userGroups) {
                    StudentGroup tempGroup = getStudentGroupByCode(g.getName());
                    if((tempGroup != null) && ((studentGroup == null) || (tempGroup.getYear() > studentGroup.getYear()))) {
                        studentGroup = tempGroup;
                    }
                }
                return studentGroup;
            } else {
                return null;
            }

        } catch (PortalException ex) {
            Logger.getLogger(LiferayUserService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SystemException ex) {
            Logger.getLogger(LiferayUserService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private StudentGroup getStudentGroupByCode(String groupCode) {
        try {
            StudentGroup sg = new StudentGroup(groupCode);
            return groupRepository.getGroupByCodeAndYearAndNumber(sg.getCode(), sg.getYear(), sg.getNumber());
        } catch (IllegalArgumentException iae) {
            return null;
        }
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
    @Resource
    private StudentGroupRepository groupRepository;
}
