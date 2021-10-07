package com.company.project.views.admin;

import com.company.project.data.Permission;
import com.company.project.security.impl.RequiresPermission;
import com.company.project.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import static com.company.project.data.Permission.ROOT;

@PageTitle("Admin")
@Route(value = "vm", layout = MainLayout.class)
@RequiresPermission(ROOT)
public class AdminView extends VerticalLayout {

	public AdminView() {
		add(new H2("Welcome to the VIP Zone :)"));

		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");
	}

}
