package com.company.project.views.welcome;

import com.company.project.security.impl.Public;
import com.company.project.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Welcome")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Public
public class WelcomeView extends HorizontalLayout {

	private final TextField name;

	public WelcomeView() {
		addClassName("welcome-view");
		name     = new TextField("Your name");
		Button sayHello = new Button("Say hello");
		add(
				new H1("This is a public section; everyone is welcome!"),
				name,
				sayHello
		);
		setVerticalComponentAlignment(Alignment.END, name, sayHello);
		sayHello.addClickListener(e -> Notification.show("Hello " + name.getValue()));
	}

}

