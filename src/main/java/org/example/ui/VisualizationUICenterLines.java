package org.example.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.service.SortingService;

import java.util.ArrayList;
import java.util.List;

public class VisualizationUICenterLines extends Application implements DisplayUI {
    private static SortingService sortingService; // Statische configuratie
    private static List<Integer> numbers; // Statische configuratie

    private final List<Line> lines = new ArrayList<>();
    private int TIMESEC = 5;
    private double WINDOW_HEIGHT = 600; // Vensterhoogte
    private double WINDOW_WIDTH = 600;  // Vensterbreedte
    private double MAX_RADIUS = 250;   // Maximale straal voor lijnen

    @Override
    public void start(Stage primaryStage) {
        if (sortingService == null || numbers == null) {
            throw new IllegalStateException("VisualizationUICenterLines is niet correct geconfigureerd. Zorg ervoor dat configure() wordt aangeroepen.");
        }

        // Bereken het middelpunt van de cirkel
        double centerX = WINDOW_WIDTH / 2;
        double centerY = WINDOW_HEIGHT / 2;

        // Bepaal de schaalfactor voor de lijnlengte
        int maxValue = numbers.stream().max(Integer::compareTo).orElse(1);
        double scaleFactor = MAX_RADIUS / maxValue;

        // Bereken hoekverschillen
        int size = numbers.size();
        double angleStep = 360.0 / size;

        // Hoofdcontainer
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #f0f8ff;");

        // Creëer lijnen
        for (int i = 0; i < size; i++) {
            double angle = Math.toRadians(i * angleStep);
            double lineLength = numbers.get(i) * scaleFactor;

            double endX = centerX + lineLength * Math.cos(angle);
            double endY = centerY + lineLength * Math.sin(angle);

            Line line = new Line(centerX, centerY, endX, endY);
            line.setStroke(Color.hsb(360.0 * i / size, 0.8, 0.8)); // Kleur gebaseerd op index
            lines.add(line);
            root.getChildren().add(line);
        }

        // Maak een timeline om de stappen te visualiseren
        List<List<Integer>> steps = sortingService.sort(new ArrayList<>(numbers)); // Genereer sorteerstappen
        Timeline timeline = createSortAnimation(steps, centerX, centerY, angleStep, scaleFactor);

        // Stel het venster in
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle("Sortering Visualisatie (Center Lijnen)");
        primaryStage.show();

        // Start de animatie
        timeline.play();
    }

    private Timeline createSortAnimation(List<List<Integer>> steps, double centerX, double centerY, double angleStep, double scaleFactor) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);

        for (int i = 0; i < steps.size(); i++) {
            List<Integer> step = steps.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.millis((TIMESEC * 1000) / steps.size() * i),
                    event -> updateLines(step, centerX, centerY, angleStep, scaleFactor));
            timeline.getKeyFrames().add(keyFrame);
        }

        return timeline;
    }

    private void updateLines(List<Integer> step, double centerX, double centerY, double angleStep, double scaleFactor) {
        for (int i = 0; i < step.size(); i++) {
            double angle = Math.toRadians(i * angleStep);
            double lineLength = step.get(i) * scaleFactor;

            double endX = centerX + lineLength * Math.cos(angle);
            double endY = centerY + lineLength * Math.sin(angle);

            Line line = lines.get(i);
            line.setEndX(endX);
            line.setEndY(endY);
        }
    }

    public void launchUI(SortingService sortingService, List<Integer> numbers) {
        VisualizationUICenterLines.sortingService = sortingService;
        VisualizationUICenterLines.numbers = numbers;
        launch();
    }
}
