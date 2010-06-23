package ua.dp.primat.schedule.admin;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import ua.dp.primat.schedule.services.LessonItem;

/**
 *
 * @author EniSh
 */
public final class EditableScheduleItemPanel extends Panel {
    public EditableScheduleItemPanel(String id, final LessonItem li) {
        super (id);

        add(new EditableLesonPanel("numerator", li.getNumerator()));
        final EditableLesonPanel elp = new EditableLesonPanel("denominator", li.getDenominator());
        elp.setVisible(!li.isOneLesson());
        elp.setOutputMarkupId(true);
        elp.setOutputMarkupPlaceholderTag(true);
        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);
        add(elp);

        
        add(new AjaxCheckBox("singleLesson", new Model<Boolean>(lessonType)) {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                lessonType = !lessonType;
                elp.setVisible(lessonType);
                if (lessonType) {
                    li.setOneLesson();
                } else {
                    li.setTwoLesson();
                }
                target.addComponent(elp);
                target.addComponent(elp.getParent());
            }
        });
    }

    private boolean lessonType;
}
