package com.smartengineering.desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        DashboardView dashboard = new DashboardView();

        Scene scene = new Scene(
                dashboard.getView(),
                1200,
                750
        );

        stage.setTitle(
                "Smart Engineering Roadmap and Resource Scheduler"
        );

        stage.setMinWidth(950);
        stage.setMinHeight(650);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}