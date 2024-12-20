package org.example.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.service.SortingService;

import java.util.ArrayList;
import java.util.List;

public class VisualizationUI extends Application implements DisplayUI {
    private static SortingService sortingService; // Statische configuratie
    private static List<Integer> numbers; // Statische configuratie

    private final List<Rectangle> bars = new ArrayList<>();
    private int TIMESEC = 10;
    private double WINDOW_HEIGHT = 600; // Vensterhoogte
    private double WINDOW_WIDTH = 1400;  // Vensterbreedte
    private double SPACING = 0;        // Spacing tussen de balken


    @Override
    public void start(Stage primaryStage) {
        if (sortingService == null || numbers == null) {
            throw new IllegalStateException("VisualizationUI is niet correct geconfigureerd. Zorg ervoor dat configure() wordt aangeroepen.");
        }

        // Bepaal de schaalfactoren voor hoogte en breedte
        int maxValue = numbers.stream().max(Integer::compareTo).orElse(1);
        double scaleFactorHeight = (WINDOW_HEIGHT - 100) / maxValue; // Houd marge voor de hoogte

        // Dynamisch de breedte berekenen per balk
        double totalWidth = numbers.size() * (SPACING + 20); // Ruimte voor balken en spacing
        double scaleFactorWidth = Math.max((WINDOW_WIDTH - 100) / numbers.size(), 2); // Minimale breedte per balk

        // Hoofdcontainer
        VBox root = new VBox();
        root.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;");

        // HBox voor de balken
        HBox barContainer = new HBox(SPACING);
        barContainer.setStyle("-fx-alignment: bottom-center;");
        barContainer.setPrefHeight(WINDOW_HEIGHT - 50);

        // Creëer visuele balken
        for (int number : numbers) {
            double barHeight = number * scaleFactorHeight; // Bereken geschaalde hoogte
            double barWidth = scaleFactorWidth; // Bereken geschaalde breedte
            Rectangle bar = new Rectangle(barWidth, barHeight); // Stel breedte en hoogte in
            bar.setFill(Color.BLUE);
            bars.add(bar);
            barContainer.getChildren().add(bar);
        }

        // Scrollbaar maken als er te veel balken zijn
        ScrollPane scrollPane = new ScrollPane(barContainer);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);

        root.getChildren().add(scrollPane);

        // Maak een timeline om de stappen te visualiseren
        List<List<Integer>> steps = sortingService.sort(new ArrayList<>(numbers)); // Genereer sorteerstappen
        Timeline timeline = createSortAnimation(steps, scaleFactorHeight);

        // Stel het venster in
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle("Sortering Visualisatie");
        primaryStage.show();

        // Start de animatie
        timeline.play();
    }

    private Timeline createSortAnimation(List<List<Integer>> steps, double scaleFactorHeight) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);

        for (int i = 0; i < steps.size(); i++) {
            List<Integer> step = steps.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.millis((TIMESEC * 1000) / steps.size() * i),
                    event -> updateBars(step, scaleFactorHeight));
            timeline.getKeyFrames().add(keyFrame);
        }

        return timeline;
    }

    private void updateBars(List<Integer> step, double scaleFactorHeight) {
        for (int i = 0; i < step.size(); i++) {
            bars.get(i).setHeight(step.get(i) * scaleFactorHeight); // Update hoogte van balken
        }
    }

    public void launchUI(SortingService sortingService, List<Integer> numbers) {
        VisualizationUI.sortingService = sortingService;
        VisualizationUI.numbers = numbers;
        launch();
    }
}
