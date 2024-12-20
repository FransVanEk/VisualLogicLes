package org.example.algorithms;

import org.example.model.SortAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class MergeSort implements SortAlgorithm {
    @Override
    public List<List<Integer>> sort(List<Integer> numbers) {
        List<List<Integer>> steps = new ArrayList<>();
        mergeSort(numbers, 0, numbers.size() - 1, steps);
        return steps;
    }

    private void mergeSort(List<Integer> numbers, int left, int right, List<List<Integer>> steps) {
        if (left < right) {
            int mid = (left + right) / 2;

            // Recursief splitsen
            mergeSort(numbers, left, mid, steps);
            mergeSort(numbers, mid + 1, right, steps);

            // Samenvoegen
            merge(numbers, left, mid, right, steps);
        }
    }

    private void merge(List<Integer> numbers, int left, int mid, int right, List<List<Integer>> steps) {
        // Grootte van de subarrays
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Tijdelijke arrays
        List<Integer> leftArray = new ArrayList<>();
        List<Integer> rightArray = new ArrayList<>();

        // Vul de tijdelijke arrays
        for (int i = 0; i < n1; i++) {
            leftArray.add(numbers.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightArray.add(numbers.get(mid + 1 + j));
        }

        // Samenvoegen van de tijdelijke arrays
        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftArray.get(i) <= rightArray.get(j)) {
                numbers.set(k, leftArray.get(i));
                i++;
            } else {
                numbers.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        // Kopieer resterende elementen van leftArray
        while (i < n1) {
            numbers.set(k, leftArray.get(i));
            i++;
            k++;
        }

        // Kopieer resterende elementen van rightArray
        while (j < n2) {
            numbers.set(k, rightArray.get(j));
            j++;
            k++;
        }

        // Voeg de huidige staat van de lijst toe aan de stappen
        steps.add(new ArrayList<>(numbers));
    }
}
