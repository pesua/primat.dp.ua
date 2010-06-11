/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.primat.schedule.admin;

import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.dp.primat.domain.Lecturer;
import ua.dp.primat.repositories.LecturerRepository;

/**
 *
 * @author Administrator
 */
public final class ManageLecturersPage extends WebPage {

    public ManageLecturersPage() {
        super();
        final List<Lecturer> lecturers = lecturerRepository.getAllLecturers();

        ListView<Lecturer> lecturerView = new ListView<Lecturer>("lecturerRow", lecturers) {

            @Override
            protected void populateItem(ListItem<Lecturer> li) {
                final Lecturer lecturer = li.getModelObject();
                add(new Label("name", lecturer.getShortName()));
                add(new Label("cathedra", lecturer.getCathedra().toString()));
                add(new Label("type", lecturer.getLecturerType().toString()));

                add(new PageLink("editLink", new IPageLink() {

                    public Page getPage() {
                        return new EditLecturerPage(lecturer);
                    }

                    public Class<? extends Page> getPageIdentity() {
                        return EditLecturerPage.class;
                    }
                }));

                add(new Link("deleteLink") {

                    @Override
                    public void onClick() {
                        lecturerRepository.delete(lecturer);
                        lecturers.remove(lecturer);
                    }
                });

            }
        };
        add(lecturerView);
        add(new Image("addLecturerImage"));
    }
    @SpringBean
    private LecturerRepository lecturerRepository;
}

