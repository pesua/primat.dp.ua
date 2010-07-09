package ua.dp.primat.schedule.admin;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.panel.Panel;
import ua.dp.primat.schedule.services.LessonItem;

/**
 * Panel which show one lesson, with numerator denominator if it needs.
 * @author EniSh
 */
public final class EditableScheduleItemPanel extends Panel {
    public EditableScheduleItemPanel(String id, LessonItem li) {
        super (id);

        lessonItem = li;

        add(new EditableLesonPanel("numerator", lessonItem.getNumerator()));
        final EditableLesonPanel elp = new EditableLesonPanel("denominator", lessonItem.getDenominator());
        secondLessonVisible = !lessonItem.isOneLesson();
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
                    lessonItem.setTwoLesson();
                } else {
                    lessonItem.setOneLesson();
                }
                target.addComponent(elp);
                //target.addComponent(elp.getParent());
            }
        });
    }

    private LessonItem lessonItem;
    private boolean secondLessonVisible;
    private static final long serialVersionUID = 1L;
}
