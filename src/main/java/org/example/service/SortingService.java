package org.example.service;


import org.example.model.SortAlgorithm;

import java.util.List;

public class SortingService {
    private final SortAlgorithm algorithm;

    public SortingService(SortAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public List<List<Integer>> sort(List<Integer> numbers) {
        return algorithm.sort(numbers);
    }
}
