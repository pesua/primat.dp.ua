package dp.ua.fpm;

import java.util.List;
import java.util.Vector;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

public final class GuestBook extends WebPage {
	private static final List<Comment> commentList = new Vector<Comment>();
	private final ListView<Comment> commentListView;
	

	public GuestBook() {
		NavomaticBorder bord = new NavomaticBorder("navomaticBorder");
		add(bord);
		bord.add(new CommentForm("commentForm"));
		bord.add(commentListView = new ListView<Comment>("comments", commentList) {
			
			public void populateItem(final ListItem<Comment> item) {
				final Comment comment = item.getModelObject();
				item.add(new Label("date", comment.date.toString()));
				item.add(new MultiLineLabel("text", comment.text));
			}
		});
	}
	
	public final class CommentForm extends Form
	{
	    private final Comment comment = new Comment();
	
	    public CommentForm(final String componentName)
	    {
	        super(componentName);
	        add(new TextArea("text", new PropertyModel(comment, "text")));
	    }
	
		public final void onSubmit() {
			final Comment newComment = new Comment();
			newComment.text = comment.text;
				
			commentList.add(0, newComment);
			commentListView.modelChanged();
			
			comment.text = "";
		}
	}
}
