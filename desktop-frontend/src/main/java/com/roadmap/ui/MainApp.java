
package com.roadmap.ui;

import com.roadmap.ui.login.LoginView;
import com.roadmap.ui.profile.ProfileView;
import com.roadmap.ui.career.CareerView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;

    public static final String BG = "#0B1120";
    public static final String CARD = "#111C30";
    public static final String ACCENT = "#6366F1";
    public static final String TEXT = "#F1F5F9";
    public static final String MUTED = "#94A3B8";

    @Override
    public void start(Stage stage) {

        primaryStage = stage;

        primaryStage.setTitle(
                "Smart Engineering Roadmap");

        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(550);

        showLogin();

        primaryStage.show();
    }

    public static Scene createScene(
            javafx.scene.Parent root,
            double width,
            double height) {

        Scene scene = new Scene(root, width, height);

        scene.setFill(
                javafx.scene.paint.Color.web(BG));

        return scene;
    }

    // LOGIN
    public static void showLogin() {

        primaryStage.setScene(
                createScene(
                        new LoginView().getView(),
                        900, 650));
    }

    // DASHBOARD
    public static void showDashboard() {

    DashboardView dashboard = new DashboardView();

    primaryStage.setScene(
            createScene(
                    dashboard,
                    1100, 800));
}

    // PROFILE
    public static void showProfile() {

        primaryStage.setScene(
                createScene(
                        new ProfileView().getView(),
                        900, 700));
    }

    // CAREER
    public static void showCareer() {

        primaryStage.setScene(
                createScene(
                        new CareerView().getView(),
                        1000, 750));
    }

    // GENERAL SCENE SWITCHING
    public static void setScene(Scene scene) {

        primaryStage.setScene(scene);
    }

    public static Stage getStage() {

        return primaryStage;
    }

    // BUTTON STYLING
    public static void styleButton(
            Button button,
            boolean primary) {

        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinHeight(45);

        button.setStyle(
                "-fx-background-color: "
                + (primary
                ? "linear-gradient(to right, #6366F1, #8B5CF6)"
                : "#1E293B")
                + ";"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 12;"
                + "-fx-cursor: hand;"
                + "-fx-padding: 10 20;");
    }

    // TEXT FIELD STYLING
    public static void styleField(
            TextInputControl field) {

        field.setMaxWidth(Double.MAX_VALUE);
        field.setMinHeight(45);

        field.setStyle(
                "-fx-background-color: #1E293B;"
                + "-fx-text-fill: #F1F5F9;"
                + "-fx-prompt-text-fill: #94A3B8;"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 10;"
                + "-fx-padding: 10;");
    }

    // CARD STYLING
    public static void styleRegion(
            Region region) {

        region.setStyle(
                "-fx-background-color: " + CARD + ";"
                + "-fx-background-radius: 18;"
                + "-fx-border-color: #25334A;"
                + "-fx-border-radius: 18;"
                + "-fx-padding: 25;");
    }

    // TITLE STYLING
    public static void styleTitle(Label label) {

        label.setStyle(
                "-fx-text-fill: " + TEXT + ";"
                + "-fx-font-size: 26px;"
                + "-fx-font-weight: bold;");
    }

    // LABEL STYLING
    public static void styleLabel(Label label) {

        label.setStyle(
                "-fx-text-fill: " + MUTED + ";"
                + "-fx-font-size: 13px;");
    }

    // MESSAGE STYLING
    public static void styleMessage(Label label) {

        label.setWrapText(true);

        label.setStyle(
                "-fx-text-fill: #A5B4FC;"
                + "-fx-font-size: 13px;");
    }

    public static void main(String[] args) {

        launch(args);
    }
}
