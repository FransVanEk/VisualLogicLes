package org.example.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.service.SortingService;

import java.util.ArrayList;
import java.util.List;

public class KaleidoscopeUI extends Application implements DisplayUI {
    private static SortingService sortingService; // Statische configuratie
    private static List<Integer> numbers; // Statische configuratie

    private final List<List<Rectangle>> quadrants = new ArrayList<>(); // Rechthoeken per kwadrant
    private static final double WINDOW_HEIGHT = 800;
    private static final double WINDOW_WIDTH = 800;
    private int DELAYMS = 30;

    @Override
    public void start(Stage primaryStage) {
        if (sortingService == null || numbers == null) {
            throw new IllegalStateException("KaleidoscopeUI is niet correct geconfigureerd. Zorg ervoor dat configure() wordt aangeroepen.");
        }

        // Bereken schaalfactoren
        int maxValue = numbers.stream().max(Integer::compareTo).orElse(1);
        double scaleFactorHeight = (WINDOW_HEIGHT / 2 - 50) / maxValue;
        double barWidth = (WINDOW_WIDTH / 2 - 50) / numbers.size();

        // Hoofdcontainer
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        // Maak de vier kwadranten
        quadrants.add(createBarChartPane(barWidth, scaleFactorHeight, 0));
        quadrants.add(createBarChartPane(barWidth, scaleFactorHeight, 90));
        quadrants.add(createBarChartPane(barWidth, scaleFactorHeight, 180));
        quadrants.add(createBarChartPane(barWidth, scaleFactorHeight, 270));

        for (int i = 0; i < 4; i++) {
            Pane pane = new Pane();
            for (Rectangle bar : quadrants.get(i)) {
                pane.getChildren().add(bar);
            }
            // Positioneer elk kwadrant
            pane.setRotate(i * 90);
            pane.setTranslateX((i == 1 || i == 3) ? WINDOW_WIDTH / 2 : 0);
            pane.setTranslateY((i >= 2) ? WINDOW_HEIGHT / 2 : 0);
            root.getChildren().add(pane);
        }

        // Animatie op basis van het sorteerproces
        List<List<Integer>> steps = sortingService.sort(new ArrayList<>(numbers));
        Timeline timeline = createSortingAnimation(steps);

        // Stel het venster in
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle("Kaleidoscope Sort");
        primaryStage.show();

        // Start de animatie
        timeline.play();
    }

    private List<Rectangle> createBarChartPane(double barWidth, double scaleFactorHeight, double rotationAngle) {
        List<Rectangle> bars = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            double barHeight = numbers.get(i) * scaleFactorHeight;
            Rectangle bar = new Rectangle(barWidth, barHeight);
            bar.setFill(Color.hsb(360.0 * i / numbers.size(), 0.8, 0.8));
            bar.setX(barWidth * i);
            bar.setY((WINDOW_HEIGHT / 2) - barHeight);
            bars.add(bar);
        }
        return bars;
    }

    private Timeline createSortingAnimation(List<List<Integer>> steps) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(steps.size());
        timeline.setAutoReverse(false);

        for (int i = 0; i < steps.size(); i++) {
            List<Integer> step = steps.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(DELAYMS * i), event -> updateBars(step));
            timeline.getKeyFrames().add(keyFrame);
        }

        return timeline;
    }

    private void updateBars(List<Integer> step) {
        double scaleFactorHeight = (WINDOW_HEIGHT / 2 - 50) / step.stream().max(Integer::compareTo).orElse(1);
        double barWidth = (WINDOW_WIDTH / 2 - 50) / step.size();

        for (int quadrant = 0; quadrant < quadrants.size(); quadrant++) {
            List<Rectangle> bars = quadrants.get(quadrant);
            for (int i = 0; i < step.size(); i++) {
                double barHeight = step.get(i) * scaleFactorHeight;
                Rectangle bar = bars.get(i);
                bar.setHeight(barHeight);
                bar.setWidth(barWidth);
                bar.setY((WINDOW_HEIGHT / 2) - barHeight);
                bar.setFill(Color.hsb(360.0 * i / step.size(), 0.8, 0.8));
            }
        }
    }

    public void launchUI(SortingService sortingService, List<Integer> numbers) {
        KaleidoscopeUI.sortingService = sortingService;
        KaleidoscopeUI.numbers = numbers;
        launch();
    }
}
