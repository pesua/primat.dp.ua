package ua.dp.primat.schedule.services;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.repositories.LecturerRepository;
import ua.dp.primat.repositories.StudentGroupRepository;

/**
 *
 * @author EniSh
 */
@Service
public class LiferayUserService {
    /**
     * returns student group which correspond owner of site. owner can be user or community
     * of users(student group)
     * @param req
     * @return
     */
    public StudentGroup studentGroupFrom(HttpServletRequest req) {
        try {
            final ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
            final Group group = GroupLocalServiceUtil.getGroup(themeDisplay.getScopeGroupId());
            if (group.isUser()) {
                final User user = UserLocalServiceUtil.getUserById(group.getClassPK());

                StudentGroup studentGroup = null;
                for (Group g : user.getGroups()) {
                    final StudentGroup tempGroup = getStudentGroupByCode(g.getName());
                    if ((tempGroup != null) && ((studentGroup == null) || (tempGroup.getYear() > studentGroup.getYear()))) {
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
            final StudentGroup sg = new StudentGroup(groupCode);
            return groupRepository.getGroupByCodeAndYearAndNumber(sg.getCode(), sg.getYear(), sg.getNumber());
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }

    /**
     * return lecturer which correspond owner of site.
     * @param req
     * @return
     */
    public Lecturer lecturerFrom(HttpServletRequest req) {
        try {
            final ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
            final Group group = GroupLocalServiceUtil.getGroup(themeDisplay.getScopeGroupId());
            if (group.isUser()) {
                final User user = UserLocalServiceUtil.getUserById(group.getClassPK());
                final long[] roleIds = user.getRoleIds();
                final long lecturerRoleId = 10504;
                if (Arrays.binarySearch(roleIds, 0, roleIds.length - 1, lecturerRoleId) == -1){
                    return null;
                } else {
                    return lecturerRepository.getLecturerByName(user.getLastName() + " " + user.getFirstName() + " " + user.getMiddleName());
                }

            } else {
                return null;
            }
        } catch (PortalException ex) {
            return null;
        } catch (SystemException ex) {
            return null;
        }
    }
    @Resource
    private StudentGroupRepository groupRepository;

    @Resource
    private LecturerRepository lecturerRepository;
}
