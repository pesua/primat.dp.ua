package dp.ua.fpm;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

public class HelloWorldApplication extends WebApplication {
	public HelloWorldApplication() {
		
	}

        @Override
	public Class<? extends WebPage> getHomePage() {
		return GuestBook.class;
	}

}
