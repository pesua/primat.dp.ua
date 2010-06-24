package ua.dp.primat.schedule.admin;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import ua.dp.primat.schedule.services.LessonItem;

/**
 * Panel which show one lesson, with numerator denominator if it needs.
 * @author EniSh
 */
public final class EditableScheduleItemPanel extends Panel {
    public EditableScheduleItemPanel(String id, final LessonItem li) {
        super (id);

        add(new EditableLesonPanel("numerator", li.getNumerator()));
        final EditableLesonPanel elp = new EditableLesonPanel("denominator", li.getDenominator());
        secondLessonVisible = !li.isOneLesson();
        elp.setVisible(secondLessonVisible);
        elp.setOutputMarkupId(true);
        elp.setOutputMarkupPlaceholderTag(true);
        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);
        add(elp);

        add(new AjaxFallbackLink<AbstractMethodError>("toggleLessonType") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                secondLessonVisible = !secondLessonVisible;
                elp.setVisible(secondLessonVisible);
                if (secondLessonVisible) {
                    li.setTwoLesson();
                } else {
                    li.setOneLesson();
                }
                target.addComponent(elp);
                target.addComponent(elp.getParent());
            }
        });
    }

    private boolean secondLessonVisible;
}
