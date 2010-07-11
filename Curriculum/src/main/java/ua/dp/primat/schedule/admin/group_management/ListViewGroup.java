/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.primat.schedule.admin.group_management;

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
 * @author Gleb
 */
public class ListViewGroup extends ListView<StudentGroup> {

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