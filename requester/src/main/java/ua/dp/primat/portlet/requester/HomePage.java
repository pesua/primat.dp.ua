package ua.dp.primat.portlet.requester;

import com.liferay.util.mail.MailEngine;
import com.liferay.util.mail.MailEngineException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;

/**
 * Homepage
 */
public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;
    // TODO Add any page properties or variables here
    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters
     *            Page parameters
     */
    private final static List PRODUCTS = Arrays.asList(new String[]{
        "win", "office", "smth else"});

    public final class SelectionForm extends Form {

        private String selectedProduct = "нет";

        public String getSelectedProduct() {
            return selectedProduct;
        }

        public void setSelectedProduct(String selectedColor) {
            this.selectedProduct = selectedColor;
        }

        public SelectionForm(String s) {
            super(s);
            selectedProduct = (String)PRODUCTS.get(0);
            //add(new Label("colorLabel", new PropertyModel(this, "selectedColor")));
            add(new DropDownChoice("product", new PropertyModel(this, "selectedProduct"), PRODUCTS));

            add(new Button("submitButton") {

                @Override
                public void onSubmit() {
                    System.out.println("Selected product: " + getSelectedProduct());

                    String from = "math.app.fpm@gmail.com";
                    String to = "math.app.fpm@gmail.com";
                    String subject = "MSDN AA request!";
                    try {
                        MailEngine.send(from, to, subject, "test-test");
                    } catch (MailEngineException ex) {
                        Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                    }


                }
            });
        }
    }
        public HomePage(final PageParameters parameters) {
            add(new SelectionForm("form"));
        }
    }
