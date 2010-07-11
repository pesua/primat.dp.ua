package ua.dp.primat.schedule.admin;

import org.apache.wicket.markup.html.WebPage;
import ua.dp.primat.repositories.RoomRepository;
import org.apache.wicket.spring.injection.annot.SpringBean;
import java.util.List;
import ua.dp.primat.domain.Room;
/**
 *
 * @author EniSh
 */
public final class ManageRooms extends WebPage {
    public ManageRooms() {
        super();
        final List<Room> rooms = roomRepository.getRooms();
        final ListViewRooms roomView = new ListViewRooms("repeating", rooms);
        add(roomView);
    }
    private static final long serialVersionUID = 1L;
    @SpringBean
    private RoomRepository roomRepository;
}