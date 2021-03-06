package com.company.project.views;

import com.company.project.data.entity.User;
import com.company.project.security.AuthenticationService;
import com.company.project.views.admin.AdminView;
import com.company.project.views.welcome.WelcomeView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * The main view is a top-level placeholder for other views.
 */
@PageTitle("Main")
public class MainLayout extends AppLayout {

	private H1                      viewTitle;
	private AuthenticationService   authenticationService;
	private AccessAnnotationChecker accessChecker;

	public MainLayout(AuthenticationService authenticationService, AccessAnnotationChecker accessChecker) {
		this.authenticationService = authenticationService;
		this.accessChecker         = accessChecker;

		setPrimarySection(Section.DRAWER);
		addToNavbar(true, createHeaderContent());
		addToDrawer(createDrawerContent());
	}

	private static RouterLink createLink(MenuItemInfo menuItemInfo) {
		RouterLink link = new RouterLink();
		link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
		link.setRoute(menuItemInfo.getView());

		Span icon = new Span();
		icon.addClassNames("me-s", "text-l");
		if (!menuItemInfo.getIconClass().isEmpty()) {
			icon.addClassNames(menuItemInfo.getIconClass());
		}

		Span text = new Span(menuItemInfo.getText());
		text.addClassNames("font-medium", "text-s");

		link.add(icon, text);
		return link;
	}

	private Component createHeaderContent() {
		DrawerToggle toggle = new DrawerToggle();
		toggle.addClassName("text-secondary");
		toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
		toggle.getElement().setAttribute("aria-label", "Menu toggle");

		viewTitle = new H1();
		viewTitle.addClassNames("m-0", "text-l");

		Header header = new Header(toggle, viewTitle);
		header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center",
		                     "w-full"
		);
		return header;
	}

	private Component createDrawerContent() {
		H2 appName = new H2("NitonCloud");
		appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");

		var section = new com.vaadin.flow.component.html.Section(
				appName,
				createNavigation(),
				createFooter()
		);
		section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
		return section;
	}

	private Nav createNavigation() {
		Nav nav = new Nav();
		nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
		nav.getElement().setAttribute("aria-labelledby", "views");

		H3 views = new H3("Views");
		views.addClassNames("flex", "h-m", "items-center", "mx-m", "my-0", "text-s", "text-tertiary");
		views.setId("views");

		// Wrap the links in a list; improves accessibility
		UnorderedList list = new UnorderedList();
		list.addClassNames("list-none", "m-0", "p-0");
		nav.add(list);

		for (RouterLink link : createLinks()) {
			ListItem item = new ListItem(link);
			list.add(item);
		}
		return nav;
	}

	private List<RouterLink> createLinks() {
		MenuItemInfo[] menuItems = new MenuItemInfo[]{ //
				new MenuItemInfo("Welcome", "la la-globe", WelcomeView.class), //

				new MenuItemInfo(
						"Admin Area",
						"la la-user",
						AdminView.class
				)

		};
		List<RouterLink> links = new ArrayList<>();
		for (MenuItemInfo menuItemInfo : menuItems) {
			if (accessChecker.hasAccess(menuItemInfo.getView())) {
				links.add(createLink(menuItemInfo));
			}
		}
		return links;
	}

	private Footer createFooter() {
		Footer layout = new Footer();
		layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

		User user = authenticationService.getAuthenticatedUser();
		if (user != null) {

			Avatar avatar = new Avatar(user.getDisplayName(), user.getProfilePictureUrl());
			avatar.addClassNames("me-xs");

			ContextMenu userMenu = new ContextMenu(avatar);
			userMenu.setOpenOnClick(true);
			userMenu.addItem("Logout", e -> {
				authenticationService.logout();
			});

			Span name = new Span(user.getDisplayName());
			name.addClassNames("font-medium", "text-s", "text-secondary");

			layout.add(avatar, name);
		} else {
			Anchor loginLink = new Anchor("login", "Sign in");
			layout.add(loginLink);
		}

		return layout;
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		viewTitle.setText(getCurrentPageTitle());
	}

	private String getCurrentPageTitle() {
		PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
		return title == null ? "" : title.value();
	}

	public static class MenuItemInfo {

		private String                     text;
		private String                     iconClass;
		private Class<? extends Component> view;

		public MenuItemInfo(String text, String iconClass, Class<? extends Component> view) {
			this.text      = text;
			this.iconClass = iconClass;
			this.view      = view;
		}

		public String getText() {
			return text;
		}

		public String getIconClass() {
			return iconClass;
		}

		public Class<? extends Component> getView() {
			return view;
		}

	}
}
