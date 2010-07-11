package ua.dp.primat.schedule.admin;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
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
            add(new FeedbackPanel("feedback"));

            final TextField number = new TextField("number");
            number.setRequired(true);
            number.add(new RangeValidator<Long>(1L, 500L));
            add(number);
            final TextField building = new TextField("building");
            building.setRequired(true);
            building.add(new RangeValidator<Long>(1L, 500L));
            add(building);
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
    private static final long serialVersionUID = 1L;
}

