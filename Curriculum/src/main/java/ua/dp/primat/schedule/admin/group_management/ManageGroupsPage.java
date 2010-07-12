package ua.dp.primat.schedule.admin.group_management;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import java.util.List;
import ua.dp.primat.domain.StudentGroup;
import ua.dp.primat.repositories.StudentGroupRepository;
import org.apache.wicket.spring.injection.annot.SpringBean;

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

    private static class ListViewGroup extends ListView<StudentGroup> {

        ListViewGroup(String id, List<StudentGroup> list) {
            super(id, list);
            groups = list;
        }

        @Override
        protected void populateItem(ListItem<StudentGroup> li) {
            final StudentGroup group = li.getModelObject();
            li.add(new Label("group", group.toString()));

            final Link editLink = new PageLink("editGroup", new IPageLink() {

                public Page getPage() {
                    return new EditGroupPage(group);
                }

                public Class<? extends Page> getPageIdentity() {
                    return EditGroupPage.class;
                }
            });
            editLink.add(new Image("editImage"));
            li.add(editLink);

            final Link deleteLink = new Link("deleteGroup") {

                @Override
                public void onClick() {
                    studentGroupRepository.remove(group);
                    groups.remove(group);
                }
            };
            deleteLink.add(new Image("deleteImage"));
            li.add(deleteLink);
        }
        @SpringBean
        private StudentGroupRepository studentGroupRepository;
        private List<StudentGroup> groups;
    }
    @SpringBean
    private StudentGroupRepository studentGroupRepository;
    private static final long serialVersionUID = 1L;
}
