package ua.dp.primat.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.dp.primat.repositories.StudentGroupRepository;
import ua.dp.primat.repositories.StudentGroupRepositoryImpl;
import static org.junit.Assert.*;

/**
 *
 * @author fdevelop
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class SpringDomainTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private StudentGroupRepository studentGroupRepository;

    public void setStudentGroupRepository(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    public SpringDomainTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGroupsCount() {
        int groupsCount = 0;//studentGroupRepository.getGroups().size();
        assertEquals("Groups available at start: " + groupsCount, 0, groupsCount);

        StudentGroup studentGroup = new StudentGroup("CO", Long.valueOf(1), Long.valueOf(2008));
        studentGroupRepository.store(studentGroup);
        groupsCount = studentGroupRepository.getGroups().size();
        assertEquals("Groups available for adding: " + groupsCount, 1, groupsCount);

        //studentGroupRepository.remove(studentGroup);
    }

    /*@Test
    public void testStudentGroup() {
        //create object
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setCode("PZ");
        studentGroup.setNumber(Long.valueOf(0));
        studentGroup.setYear(Long.valueOf(2009));

        //store
        studentGroupRepository.store(studentGroup);
        studentGroupRepository.getEm().flush();

        //check
        final int preparedCount = 1;
        int totalCount = studentGroupRepository.getGroups().size();
        assertEquals("Test ready.", totalCount, preparedCount);
    }*/

}