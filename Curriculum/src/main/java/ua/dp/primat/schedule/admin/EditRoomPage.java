package ua.dp.primat.schedule.admin;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.Room;
import ua.dp.primat.repositories.RoomRepository;

/**
 *
 * @author EniSh
 */
public final class EditRoomPage extends WebPage {

    public EditRoomPage() {
        this(new Room());
    }

    public EditRoomPage(Room room) {
        super();
        this.room = room;

        add(new EditRoomForm("room"));
    }

    private class EditRoomForm extends Form<Room> {

        public EditRoomForm(String cName) {
            super(cName, new CompoundPropertyModel<Room>(room));

            add(new TextField("number"));
            add(new TextField("building"));
        }

        @Override
        protected void onSubmit() {
            super.onSubmit();
            roomRepository.store(room);
            setResponsePage(ManageRooms.class);
        }
    }

    private Room room;

    @SpringBean
    private RoomRepository roomRepository;
}

