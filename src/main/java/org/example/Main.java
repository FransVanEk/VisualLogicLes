package org.example;
import org.example.algorithms.BubbleSort;
import org.example.service.NumberGenerator;
import org.example.service.SortingService;
import org.example.ui.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        var bubbleSort = new BubbleSort();
        var sortingService = new SortingService(bubbleSort);
        List<Integer> numbers = new NumberGenerator().generate(100,1,100);

        DisplayUI visualization = new VisualizationUI();
        visualization.launchUI(sortingService, numbers);
    }
}
