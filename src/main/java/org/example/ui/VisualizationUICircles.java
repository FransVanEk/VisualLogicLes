package org.example.ui;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.service.SortingService;

import java.util.ArrayList;
import java.util.List;

public class VisualizationUICircles extends Application implements DisplayUI {
    private static SortingService sortingService; // Statische configuratie
    private static List<Integer> numbers; // Statische configuratie

    private final List<Circle> circles = new ArrayList<>();
    private int TIMESEC = 5;
    private double WINDOW_HEIGHT = 600; // Vensterhoogte
    private double WINDOW_WIDTH = 800;  // Vensterbreedte

    @Override
    public void start(Stage primaryStage) {
        if (sortingService == null || numbers == null) {
            throw new IllegalStateException("VisualizationUICircles is niet correct geconfigureerd. Zorg ervoor dat configure() wordt aangeroepen.");
        }

        // Bepaal de schaalfactoren voor de cirkels
        int maxValue = numbers.stream().max(Integer::compareTo).orElse(1);
        double radiusScaleFactor = (WINDOW_HEIGHT / 3) / maxValue; // Maximaal 1/3 van de hoogte voor de grootste cirkel
        double spacing = WINDOW_WIDTH / numbers.size(); // Verdeel de cirkels gelijkmatig over de breedte

        // Hoofdcontainer
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #f0f8ff;");

        // Creëer cirkels
        for (int i = 0; i < numbers.size(); i++) {
            double radius = numbers.get(i) * radiusScaleFactor;
            Circle circle = new Circle(spacing * i + spacing / 2, WINDOW_HEIGHT / 2, radius);
            circle.setFill(Color.hsb(360.0 * i / numbers.size(), 0.8, 0.8));
            circles.add(circle);
            root.getChildren().add(circle);
        }

        // Maak een timeline om de stappen te visualiseren
        List<List<Integer>> steps = sortingService.sort(new ArrayList<>(numbers)); // Genereer sorteerstappen
        Timeline timeline = createSortAnimation(steps, radiusScaleFactor, spacing);

        // Stel het venster in
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle("Sortering Visualisatie (Cirkels)");
        primaryStage.show();

        // Start de animatie
        timeline.play();
    }

    private Timeline createSortAnimation(List<List<Integer>> steps, double radiusScaleFactor, double spacing) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);

        for (int i = 0; i < steps.size(); i++) {
            List<Integer> step = steps.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.millis((TIMESEC * 1000) / steps.size() * i),
                    event -> updateCircles(step, radiusScaleFactor, spacing));
            timeline.getKeyFrames().add(keyFrame);
        }

        return timeline;
    }

    private void updateCircles(List<Integer> step, double radiusScaleFactor, double spacing) {
        for (int i = 0; i < step.size(); i++) {
            double newRadius = step.get(i) * radiusScaleFactor;
            circles.get(i).setRadius(newRadius); // Update straal van de cirkel
            circles.get(i).setCenterX(spacing * i + spacing / 2); // Update de positie op basis van volgorde
        }
    }

    public void launchUI(SortingService sortingService, List<Integer> numbers) {
        VisualizationUICircles.sortingService = sortingService;
        VisualizationUICircles.numbers = numbers;
        launch();
    }
}
