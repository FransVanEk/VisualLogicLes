package org.example.ui;

import org.example.service.SortingService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AsciiFileUI implements DisplayUI {
    private static SortingService sortingService; // Statische configuratie
    private static List<Integer> numbers; // Statische configuratie
    private static String outputFilePath = "sort_output.txt"; // Standaard uitvoerbestand


    public void launchUI(SortingService sortingService, List<Integer> numbers) {
        AsciiFileUI.sortingService = sortingService;
        AsciiFileUI.numbers = numbers;
        try {
            writeToFile();
        } catch (IOException e) {
            System.err.println("Fout bij het schrijven naar bestand: " + e.getMessage());
        }
    }

    private void writeToFile() throws IOException {
        System.out.println("Begin schrijven naar bestand...");
        List<List<Integer>> steps = sortingService.sort(numbers);

        System.out.println("Aantal stappen: " + steps.size());

        StringBuilder output = new StringBuilder();
        output.append("Sortering uitvoer in ASCII-art:\n");
        output.append("================================\n\n");

        for (int stepIndex = 0; stepIndex < steps.size(); stepIndex++) {
            List<Integer> step = steps.get(stepIndex);
            output.append("Stap ").append(stepIndex + 1).append(":\n");
            output.append(asciiArt(step)).append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(output.toString());
        }

        System.out.println("Uitvoer succesvol weggeschreven naar: " + outputFilePath);
    }



    private String asciiArt(List<Integer> numbers) {
        int maxValue = numbers.stream().max(Integer::compareTo).orElse(1);
        StringBuilder builder = new StringBuilder();

        for (int row = maxValue; row > 0; row--) {
            for (int number : numbers) {
                builder.append(number >= row ? "█" : " ").append(" ");
            }
            builder.append("\n");
        }

        builder.append("-".repeat(numbers.size() * 2)).append("\n");

        for (int number : numbers) {
            builder.append(number).append(" ");
        }
        builder.append("\n");

        return builder.toString();
    }

}
