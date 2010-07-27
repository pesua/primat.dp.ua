package ua.dp.primat.schedule.admin;

import java.util.Collections;
import java.util.List;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import ua.dp.primat.domain.StudentGroup;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dp.primat.repositories.StudentGroupRepository;

public class AdminHomePageTest {
    
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    WicketTester tester;

    public AdminHomePageTest() {
    }

    @Test
    public void testHomePage() {
        AnnotApplicationContextMock aacm = new AnnotApplicationContextMock();
        aacm.putBean("studentGroupRepository", new StudentGroupRepositoryMock());

        tester = new WicketTester();

        tester.getApplication().addComponentInstantiationListener(
                new SpringComponentInjector(tester.getApplication(), aacm));

        tester.startPage(new AdminHomePage());
        tester.assertRenderedPage(AdminHomePage.class);
    }

    private class StudentGroupRepositoryMock implements StudentGroupRepository {

        public void store(StudentGroup studentGroup) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void remove(StudentGroup studentGroup) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public List<StudentGroup> getGroups() {
            return Collections.EMPTY_LIST;
        }

    }
}
