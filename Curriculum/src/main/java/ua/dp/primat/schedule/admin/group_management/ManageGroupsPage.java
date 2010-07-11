package ua.dp.primat.schedule.admin.group_management;

import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.repositories.StudentGroupRepository;

/**
 *
 * @author EniSh
 */
public final class ManageGroupsPage extends WebPage {

    public ManageGroupsPage() {
        super();
        final List<StudentGroup> groups = studentGroupRepository.getGroups();
        final ListViewGroup roomView = new ListViewGroup("repeating", groups);
        add(roomView);
    }
    @SpringBean
    private StudentGroupRepository studentGroupRepository;
    private static final long serialVersionUID = 1L;
}

