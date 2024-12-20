package org.example.algorithms;

import java.util.ArrayList;
import java.util.List;
import org.example.model.SortAlgorithm;

public class BubbleSort implements SortAlgorithm {
    @Override
    public List<List<Integer>> sort(List<Integer> numbers) {
        List<List<Integer>> steps = new ArrayList<>();
        List<Integer> mutableList = new ArrayList<>(numbers); // Maak een veranderbare lijst
        steps.add(new ArrayList<>(mutableList)); // Eerste stap toevoegen

        for (int i = 0; i < mutableList.size() - 1; i++) {
            for (int j = 0; j < mutableList.size() - i - 1; j++) {
                if (mutableList.get(j) > mutableList.get(j + 1)) {
                    // Wisselen van elementen
                    int temp = mutableList.get(j);
                    mutableList.set(j, mutableList.get(j + 1));
                    mutableList.set(j + 1, temp);
                }
                steps.add(new ArrayList<>(mutableList)); // Toevoegen huidige status
            }
        }
        return steps;
    }

}
