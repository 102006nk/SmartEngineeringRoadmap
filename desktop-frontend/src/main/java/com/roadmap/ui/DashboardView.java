package com.roadmap.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class DashboardView extends BorderPane {

    private final String BG = "#F4F7FB";
    private final String NAV = "#111827";
    private final String BLUE = "#2563EB";

    public DashboardView() {
        setStyle("-fx-background-color: " + BG + ";");

        setLeft(createSidebar());
        setCenter(createContent());
    }

    private VBox createSidebar() {

        VBox sidebar = new VBox(25);
        sidebar.setPadding(new Insets(30, 20, 25, 20));
        sidebar.setPrefWidth(245);
        sidebar.setStyle("-fx-background-color: " + NAV + ";");

        Label logo = new Label("⚡ Smart Roadmap");
        logo.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 22px;" +
            "-fx-font-weight: bold;"
        );

        Label menuLabel = new Label("MENU");
        menuLabel.setStyle(
            "-fx-text-fill: #9CA3AF;" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );

        VBox menu = new VBox(12);

        menu.getChildren().addAll(
            navButton("⌂   Dashboard", true, MainApp::showDashboard),
            navButton("◎   My Profile", false, MainApp::showProfile),
            navButton("▤   Explore Careers", false, MainApp::showCareer),
            navButton("▣   My Roadmap", false, MainApp::showCareer)
        );

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logout = new Button("↪   Logout");
        logout.setMaxWidth(Double.MAX_VALUE);
        logout.setAlignment(Pos.CENTER_LEFT);

        logout.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #FCA5A5;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 12px;"
        );

        logout.setOnAction(e -> MainApp.showLogin());

        sidebar.getChildren().addAll(
            logo,
            menuLabel,
            menu,
            spacer,
            logout
        );

        return sidebar;
    }

    private Button navButton(
            String text,
            boolean active,
            Runnable action) {

        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);

        if (active) {
            button.setStyle(
                "-fx-background-color: #2563EB;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 14px;" +
                "-fx-background-radius: 10px;"
            );
        } else {
            button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #D1D5DB;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 14px;" +
                "-fx-background-radius: 10px;"
            );
        }

        button.setOnAction(e -> action.run());

        return button;
    }

    private VBox createContent() {

        VBox content = new VBox(25);
        content.setPadding(new Insets(35));
        content.setStyle("-fx-background-color: " + BG + ";");

        Label heading = new Label("Dashboard");
        heading.setStyle(
            "-fx-font-size: 28px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label subtitle = new Label(
            "Your engineering career journey starts here."
        );
        subtitle.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #6B7280;"
        );

        VBox welcome = new VBox(10);

        Label welcomeTitle = new Label("Welcome back, Engineer! 👋");
        welcomeTitle.setStyle(
            "-fx-font-size: 24px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;"
        );

        Label welcomeText = new Label(
            "Build skills, explore careers and plan your future."
        );
        welcomeText.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;"
        );

        Button explore = new Button("Explore Career Paths →");
        explore.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #2563EB;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 12px 18px;" +
            "-fx-background-radius: 8px;"
        );

        explore.setOnAction(e -> MainApp.showCareer());

        welcome.getChildren().addAll(
            welcomeTitle,
            welcomeText,
            explore
        );

        welcome.setPadding(new Insets(28));
        welcome.setStyle(
            "-fx-background-color: linear-gradient(to right, #2563EB, #4F46E5);" +
            "-fx-background-radius: 18px;"
        );

        HBox stats = new HBox(20);

        stats.getChildren().addAll(
            statCard("My Skills", "—", "Skills in your profile"),
            statCard("Roadmap", "—", "Learning plan status"),
            statCard("Study Hours", "—", "Your learning progress")
        );

        Label quickTitle = new Label("Quick Actions");
        quickTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        HBox actions = new HBox(20);

        actions.getChildren().addAll(
            actionCard(
                "🎯",
                "Explore Careers",
                "Find engineering careers that match your interests.",
                MainApp::showCareer
            ),
            actionCard(
                "👤",
                "Update Profile",
                "Manage your branch, semester and skills.",
                MainApp::showProfile
            ),
            actionCard(
                "📚",
                "My Roadmap",
                "Explore your career learning path.",
                MainApp::showCareer
            )
        );

        Label tipTitle = new Label("Career Tip");
        tipTitle.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label tipText = new Label(
            "Consistency matters more than speed. " +
            "Learn one new skill and practice it every day."
        );

        tipText.setWrapText(true);
        tipText.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #4B5563;"
        );

        VBox tip = new VBox(10, tipTitle, tipText);
        tip.setPadding(new Insets(22));
        tip.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15px;"
        );

        content.getChildren().addAll(
            heading,
            subtitle,
            welcome,
            stats,
            quickTitle,
            actions,
            tip
        );

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: " + BG + ";");

        VBox wrapper = new VBox(scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        return wrapper;
    }

    private VBox statCard(
            String title,
            String value,
            String description) {

        Label titleLabel = new Label(title);
        titleLabel.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #6B7280;"
        );

        Label valueLabel = new Label(value);
        valueLabel.setStyle(
            "-fx-font-size: 28px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label descLabel = new Label(description);
        descLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #9CA3AF;"
        );

        VBox card = new VBox(12);
        card.getChildren().addAll(titleLabel, valueLabel, descLabel);

        card.setPadding(new Insets(22));
        card.setPrefWidth(220);
        card.setMaxWidth(Double.MAX_VALUE);

        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15px;"
        );

        HBox.setHgrow(card, Priority.ALWAYS);

        return card;
    }

    private VBox actionCard(
            String icon,
            String title,
            String description,
            Runnable action) {

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 26px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-text-fill: #6B7280;"
        );

        VBox card = new VBox(12);
        card.getChildren().addAll(iconLabel, titleLabel, descLabel);

        card.setPadding(new Insets(22));
        card.setPrefWidth(230);
        card.setMinHeight(175);
        card.setMaxWidth(Double.MAX_VALUE);

        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15px;"
        );

        card.setOnMouseClicked(e -> action.run());

        HBox.setHgrow(card, Priority.ALWAYS);

        return card;
    }
}