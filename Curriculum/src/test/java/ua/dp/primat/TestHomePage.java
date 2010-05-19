package ua.dp.primat;

import junit.framework.TestCase;
import org.apache.wicket.util.tester.WicketTester;
import ua.dp.primat.curriculum.HomePage;
import ua.dp.primat.curriculum.WicketApplication;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	@Override
	public void setUp()
	{
            tester = new WicketTester(new WicketApplication());
	}

	public void testRenderMyPage()
	{
            //start and render the test page
            tester.startPage(HomePage.class);

            //assert rendered page class
            tester.assertRenderedPage(HomePage.class);
	}
}
