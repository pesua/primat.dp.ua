package ua.dp.primat;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.dp.primat.curriculum.HomePage;
import ua.dp.primat.curriculum.WicketApplication;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage {
	private WicketTester tester;

        /*
         * Basic set up of the test
         */
	@Before
	public void setUp()
	{
            tester = new WicketTester(new WicketApplication());

            //start and render the test page
            tester.startPage(HomePage.class);
	}

        @Test
	public void testRenderMyPage()
	{
            //assert rendered page class
            tester.assertRenderedPage(HomePage.class);

            System.out.println(tester.getApplication().getHomePage());

            DropDownChoice dChoice = (DropDownChoice) tester.getComponentFromLastRenderedPage("form:semester");
            Assert.assertTrue(dChoice.getChoices().size() == 8);

            dChoice = (DropDownChoice) tester.getComponentFromLastRenderedPage("form:group");
            Assert.assertTrue(dChoice.getChoices().size() > 0);
	}
}
