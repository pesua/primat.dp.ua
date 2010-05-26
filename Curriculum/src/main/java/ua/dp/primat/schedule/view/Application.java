/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.view;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 *
 * @author Administrator
 */
public class Application extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return ViewDailySchedule.class;
    }

}
