package org.test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("org.test.MyAppWidgetset")
public class MyUI extends UI {

	private Grid grid = new Grid();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
            Notification.show("Hello!", Type.WARNING_MESSAGE);
            layout.addComponent(new Label("Thanks again and again" + name.getValue() 
            + ", it works!"));
            
            ConfirmDialog.show(this, "Please Confirm:", "Are you really sure?",
                    "I am", "Not quite", new ConfirmDialog.Listener() {

                        public void onClose(ConfirmDialog dialog) {
                            if (dialog.isConfirmed()) {
                                // Confirmed to continue
                                feedback(dialog.isConfirmed());
                            } else {
                                // User did not confirm
                                feedback(dialog.isConfirmed());
                            }
                        }
                    });
    
        });
        
        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        layout.addComponents(grid);
        List<MeskData> mesks = new ArrayList<>();
        mesks.add(new MeskData("aba", true));
        mesks.add(new MeskData("meev", false));
        grid.setContainerDataSource(new BeanItemContainer<>(MeskData.class, mesks));
        
        setContent(layout);
    }

    public static class MeskData {
    	private String name;
    	private boolean refed;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isRefed() {
			return refed;
		}
		public void setRefed(boolean refed) {
			this.refed = refed;
		}
		public MeskData(String name, boolean refed) {
			super();
			this.name = name;
			this.refed = refed;
		}
    	
    }
    protected void feedback(boolean confirmed) {
    	Notification.show("Conf=" + confirmed, "Click here to continue", Type.ERROR_MESSAGE);
		// TODO Auto-generated method stub
		
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
