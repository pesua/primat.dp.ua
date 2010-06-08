/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.admin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.schedule.data.Room;
import ua.dp.primat.schedule.data.RoomRepository;
import ua.dp.primat.schedule.data.RoomRepositoryImpl;

/**
 *
 * @author Administrator
 */
public final class ManageRooms extends WebPage {
    public ManageRooms() {
        super ();
        List<Room> rooms = roomRepository.getRooms();

        /*add(new PageLink("addRoomLink", new IPageLink() {

            public Page getPage() {
                return new EditRoom();
            }

            public Class<? extends Page> getPageIdentity() {
                return EditRoom.class;
            }
        }));*/

        ListView<Room> roomView = new ListView<Room>("repeating", rooms) {

            @Override
            protected void populateItem(ListItem<Room> li) {
                Room room = li.getModelObject();
                li.add(new Label("number", room.getNumber().toString()));
                li.add(new Label("building", room.getBuilding().toString()));
                li.add(new Label("action"));
            }
        };
        add(roomView);
    }

    @SpringBean
    private RoomRepositoryImpl roomRepository;
}

