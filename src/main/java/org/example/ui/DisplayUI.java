package org.example.ui;

import org.example.service.SortingService;

import java.util.List;

public interface DisplayUI {

     void launchUI(SortingService sortingService, List<Integer> numbers);
}
