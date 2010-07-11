/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.primat.schedule.admin;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import ua.dp.primat.domain.Room;
import java.util.List;
import ua.dp.primat.repositories.RoomRepository;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 *
 * @author Gleb
 */
public class ListViewRooms extends ListView<Room> {

    ListViewRooms(String id, List<Room> list) {
        super(id, list);
        rooms = list;
    }
    
    @Override
    protected void populateItem(ListItem<Room> li) {
        final Room room = li.getModelObject();
        li.add(new Label("room", room.toString()));

        final Link editLink = new PageLink("editRoom", new IPageLink() {

            public Page getPage() {
                return new EditRoomPage(room);
            }

            public Class<? extends Page> getPageIdentity() {
                return EditRoomPage.class;
            }
        });
        editLink.add(new Image("editImage"));
        li.add(editLink);

        final Link deleteLink = new Link("deleteRoom") {

            @Override
            public void onClick() {
                roomRepository.delete(room);
                rooms.remove(room);
            }
        };
        deleteLink.add(new Image("deleteImage"));
        li.add(deleteLink);
    }
    @SpringBean
    private RoomRepository roomRepository;
    private List<Room> rooms;
}