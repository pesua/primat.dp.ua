package ua.dp.primat.schedule.view;
import java.util.Date;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public final class ViewDailySchedule extends WebPage {
    public ViewDailySchedule() {
        add(new Label("day", "Day is: " + (new Date()).getDay()));
    }
}

