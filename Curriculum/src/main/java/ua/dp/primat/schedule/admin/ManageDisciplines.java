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
import ua.dp.primat.domain.workload.Discipline;
import ua.dp.primat.repositories.DisciplineRepository;

/**
 *
 * @author EniSh
 */
public final class ManageDisciplines extends WebPage {

    private static final long serialVersionUID = 1L;

    public ManageDisciplines() {
        super ();

        final List<Discipline> disciplines = disciplineRepository.getDisciplines();

        final ListView<Discipline> disciplineView = new DisciplineListView("repeating", disciplines);

        add(disciplineView);
    }

    @SpringBean
    private DisciplineRepository disciplineRepository;

    private class EditDisciplinePageLink implements IPageLink {

        public EditDisciplinePageLink(Discipline discipline) {
            this.discipline = discipline;
        }

        public Page getPage() {
            return new EditDisciplinePage(discipline);
        }

        public Class<? extends Page> getPageIdentity() {
            return EditDisciplinePage.class;
        }

        private Discipline discipline;

    }

    private class DisciplineListView extends ListView<Discipline> {

        private final List<Discipline> disciplines;

        public DisciplineListView(String id, List<Discipline> list) {
            super(id, list);
            this.disciplines = list;
        }

        @Override
        protected void populateItem(ListItem<Discipline> li) {
            final Discipline discipline = li.getModelObject();
            li.add(new Label("disciplineName", discipline.getName()));
            li.add(new Label("disciplineCathedra", discipline.getCathedra().toString()));
            final Link editLink = new PageLink("editDiscipline",
                    new EditDisciplinePageLink(discipline));
            editLink.add(new Image("editImage"));
            li.add(editLink);
            final Link deleteLink = new Link("deleteDiscipline") {

                @Override
                public void onClick() {
                    disciplineRepository.delete(discipline);
                    disciplines.remove(discipline);
                }
            };
            deleteLink.add(new Image("deleteImage"));
            li.add(deleteLink);
        }
        private static final long serialVersionUID = 1L;
    }
}

