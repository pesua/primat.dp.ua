/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.schedule.admin;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import ua.dp.primat.schedule.services.LessonItem;

/**
 *
 * @author Administrator
 */
public final class EditableScheduleItemPanel extends Panel {
    public EditableScheduleItemPanel(String id, LessonItem li) {
        super (id);
        
        add(new EditableLesonPanel("numerator", li.getNumerator()));
        EditableLesonPanel elp = new EditableLesonPanel("denominator", li.getDenominator());
        elp.setVisible(!li.isOneLesson());
        add(elp);
    }
}
