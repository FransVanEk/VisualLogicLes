package org.example.ui;

import org.example.service.SortingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements DisplayUI {
    private SortingService sortingService;
    private List<Integer> numbers;

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Indien geen lijst is meegegeven, vraag invoer van gebruiker
        if (numbers == null || numbers.isEmpty()) {
            System.out.println("Voer een lijst met getallen in, gescheiden door spaties:");
            String input = scanner.nextLine();
            numbers = new ArrayList<>(
                    List.of(input.split(" "))
                            .stream()
                            .map(Integer::parseInt)
                            .toList()
            );
        }

        System.out.println("Oorspronkelijke lijst: " + numbers);

        // Voer sorteeroperatie uit
        var steps = sortingService.sort(numbers);

        // Toon stappen in de console
        System.out.println("Sorteren stap-voor-stap:");
        for (var step : steps) {
            System.out.println(step);
        }
    }
    @Override
    public void launchUI(SortingService sortingService, List<Integer> numbers) {
        this.sortingService = sortingService;
        this.numbers = numbers;
        start();
    }

}
