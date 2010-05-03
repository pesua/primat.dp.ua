package dp.ua.fpm;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{
	String text;
	Date date = new Date();
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
