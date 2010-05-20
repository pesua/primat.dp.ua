package ua.dp.primat;

import junit.framework.TestCase;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.dp.primat.curriculum.view.HomePage;
import ua.dp.primat.curriculum.view.WicketApplication;

public class TestHomePage extends TestCase{
	private WicketTester tester;

 	@Before
	public void setUp()
	{
            tester = new WicketTester(new WicketApplication());

            tester.startPage(HomePage.class);
	}

        @Test
	public void testRenderMyPage()
	{
            tester.assertRenderedPage(HomePage.class);

            System.out.println(tester.getApplication().getHomePage());

            DropDownChoice dChoice = (DropDownChoice) tester.getComponentFromLastRenderedPage("form:semester");
            Assert.assertTrue("Not valid number of semesters in list", dChoice.getChoices().size() == 8);

            dChoice = (DropDownChoice) tester.getComponentFromLastRenderedPage("form:group");
            Assert.assertTrue("No groups in the list", dChoice.getChoices().size() > 0);
	}
}
