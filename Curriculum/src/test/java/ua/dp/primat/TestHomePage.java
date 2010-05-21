package ua.dp.primat;

import junit.framework.TestCase;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.util.tester.WicketTester;
import junit.framework.Assert;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.junit.Before;
import org.junit.Test;
import ua.dp.primat.curriculum.view.HomePage;

public class TestHomePage extends TestCase {

    private WicketTester tester;

    @Before
    @Override
    public void setUp() {
        ApplicationContextMock acm = new ApplicationContextMock();

        tester = new WicketTester();
        tester.getApplication().addComponentInstantiationListener(new SpringComponentInjector(tester.getApplication(), acm));

//        tester.startPage(HomePage.class);
    }

    @Test
    public void testRenderMyPage() {
        /*tester.assertRenderedPage(HomePage.class);

        System.out.println(tester.getApplication().getHomePage());

        DropDownChoice dChoice = (DropDownChoice) tester.getComponentFromLastRenderedPage("form:semester");
        Assert.assertTrue("Not valid number of semesters in list", dChoice.getChoices().size() == 8);

        dChoice = (DropDownChoice) tester.getComponentFromLastRenderedPage("form:group");
        Assert.assertTrue("No groups in the list", dChoice.getChoices().size() > 0);*/
    }
}
