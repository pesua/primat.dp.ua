package dp.ua.fpm;

import junit.framework.TestCase;
import org.apache.wicket.util.tester.WicketTester;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	@Override
	public void setUp()
	{
		tester = new WicketTester(new HelloWorldApplication());
	}

	public void testRenderMyPage()
	{
		//start and render the test page
		tester.startPage(GuestBook.class);

		//assert rendered page class
                tester.assertRenderedPage(GuestBook.class);

		//assert rendered label component
		//tester.assertLabel("message", "If you see this message wicket is properly configured and running");
	}
}
