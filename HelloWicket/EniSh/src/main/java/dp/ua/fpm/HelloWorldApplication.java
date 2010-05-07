package dp.ua.fpm;

import org.apache.wicket.protocol.http.WebApplication;

public class HelloWorldApplication extends WebApplication {
	public HelloWorldApplication() {
		
	}
	
	public Class getHomePage() {
		return GuestBook.class;
	}

}
