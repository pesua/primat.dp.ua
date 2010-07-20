package ua.dp.primat.schedule.admin;

import java.util.List;
import org.apache.wicket.Page;
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

        final ListView<Lecturer> lecturerView = new LecturerListView("lecturerRow", lecturers, lecturers);
        add(lecturerView);
    }

    @SpringBean
    private LecturerRepository lecturerRepository;

    private static final long serialVersionUID = 1L;

    private class EditLecturerPageLink implements IPageLink {

        public EditLecturerPageLink(Lecturer lecturer) {
            this.lecturer = lecturer;
        }

        public Page getPage() {
            return new EditLecturerPage(lecturer);
        }

        public Class<? extends Page> getPageIdentity() {
            return EditLecturerPage.class;
        }

        private Lecturer lecturer;

    }

    private class LecturerListView extends ListView<Lecturer> {

        private final List<Lecturer> lecturers;

        public LecturerListView(String id, List<? extends Lecturer> list, List<Lecturer> lecturers) {
            super(id, list);
            this.lecturers = lecturers;
        }

        @Override
        protected void populateItem(ListItem<Lecturer> li) {
            final Lecturer lecturer = li.getModelObject();
            li.add(new Label("name", lecturer.getShortName()));
            li.add(new Label("cathedra", lecturer.getCathedra().toString()));
            li.add(new Label("type", lecturer.getLecturerType().toString()));
            final Link editLink = new PageLink("editLink", new EditLecturerPageLink(lecturer));
            li.add(editLink);
            editLink.add(new Image("editImage"));
            final Link deleteLink = new Link("deleteLink") {

                @Override
                public void onClick() {
                    lecturerRepository.delete(lecturer);
                    lecturers.remove(lecturer);
                }
            };
            deleteLink.add(new Image("deleteImage"));
            li.add(deleteLink);
        }
        private static final long serialVersionUID = 2L;
    }
}
