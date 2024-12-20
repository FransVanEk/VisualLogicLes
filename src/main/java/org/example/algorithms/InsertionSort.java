package org.example.algorithms;


import org.example.model.SortAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class InsertionSort implements SortAlgorithm {
    @Override
    public List<List<Integer>> sort(List<Integer> numbers) {
        List<List<Integer>> steps = new ArrayList<>();
        List<Integer> sortedList = new ArrayList<>(numbers); // Maak een kopie van de lijst
        steps.add(new ArrayList<>(sortedList)); // Eerste stap toevoegen

        for (int i = 1; i < sortedList.size(); i++) {
            int key = sortedList.get(i);
            int j = i - 1;

            // Verplaats elementen die groter zijn dan de sleutel
            while (j >= 0 && sortedList.get(j) > key) {
                sortedList.set(j + 1, sortedList.get(j));
                j = j - 1;
            }
            sortedList.set(j + 1, key);
            steps.add(new ArrayList<>(sortedList)); // Voeg de huidige status toe aan de stappen
        }

        return steps;
    }
}
