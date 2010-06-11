/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.primat.schedule.admin;

import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.Room;
import ua.dp.primat.repositories.RoomRepository;

/**
 *
 * @author Administrator
 */
public final class ManageRooms extends WebPage {

    public ManageRooms() {
        super();
        final List<Room> rooms = roomRepository.getRooms();

        ListView<Room> roomView = new ListView<Room>("repeating", rooms) {

            @Override
            protected void populateItem(ListItem<Room> li) {
                final Room room = li.getModelObject();
                li.add(new Label("room", room.toString()));
                li.add(new PageLink("editRoom", new IPageLink() {

                    public Page getPage() {
                        return new EditRoomPage(room);
                    }

                    public Class<? extends Page> getPageIdentity() {
                        return EditRoomPage.class;
                    }
                }));
                li.add(new Link("deleteRoom") {

                    @Override
                    public void onClick() {
                        roomRepository.delete(room);
                        rooms.remove(room);
                    }
                });

                add(new Image("editImage"));
                add(new Image("deleteImage"));
            }
        };
        add(roomView);
    }
    @SpringBean
    private RoomRepository roomRepository;
}

