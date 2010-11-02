/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.view;

import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 * @author pesua
 */
public class ShedulePanel extends Panel{    
    public ShedulePanel(final String id){
        super(id);
    }

    public boolean isGroupVisible() {
        return groupVisible;
    }

    public void setGroupVisible(boolean groupVisible) {
        this.groupVisible = groupVisible;
    }

    public boolean isLecturerVisible() {
        return lecturerVisible;
    }

    public void setLecturerVisible(boolean lecturerVisible) {
        this.lecturerVisible = lecturerVisible;
    }

    public boolean isRoomVisible() {
        return roomVisible;
    }

    public void setRoomVisible(boolean roomVisible) {
        this.roomVisible = roomVisible;
    }


    private boolean lecturerVisible = true;
    private boolean roomVisible = true;
    private boolean groupVisible = true;
    
    private static final long serialVersionUID = 1L;
}
