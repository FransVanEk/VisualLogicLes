package org.example.algorithms;

import java.util.ArrayList;
import java.util.List;
import org.example.model.SortAlgorithm;
public class SelectionSort implements SortAlgorithm {
    @Override
    public List<List<Integer>> sort(List<Integer> numbers) {
        List<List<Integer>> steps = new ArrayList<>();
        List<Integer> sortedList = new ArrayList<>(numbers); // Maak een kopie van de lijst
        steps.add(new ArrayList<>(sortedList)); // Eerste stap toevoegen

        for (int i = 0; i < sortedList.size() - 1; i++) {
            int minIndex = i;

            // Zoek het minimum in het ongesorteerde deel
            for (int j = i + 1; j < sortedList.size(); j++) {
                if (sortedList.get(j) < sortedList.get(minIndex)) {
                    minIndex = j;
                }
            }

            // Wissel het gevonden minimum met het huidige element
            if (minIndex != i) {
                int temp = sortedList.get(i);
                sortedList.set(i, sortedList.get(minIndex));
                sortedList.set(minIndex, temp);
            }

            steps.add(new ArrayList<>(sortedList)); // Voeg de huidige status toe aan de stappen
        }

        return steps;
    }
}
