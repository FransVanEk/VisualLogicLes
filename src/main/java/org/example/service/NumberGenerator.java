package org.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberGenerator {
    private final Random random;

    public NumberGenerator() {
        this.random = new Random();
    }

    /**
     * Genereert een lijst met willekeurige gehele getallen.
     *
     * @param count Het aantal getallen dat gegenereerd moet worden.
     * @param min   De minimumwaarde (inclusief).
     * @param max   De maximumwaarde (inclusief).
     * @return Een lijst met willekeurige gehele getallen.
     */
    public List<Integer> generate(int count, int min, int max) {
        if (count <= 0) {
            throw new IllegalArgumentException("Het aantal getallen moet groter zijn dan 0.");
        }
        if (min > max) {
            throw new IllegalArgumentException("De minimumwaarde mag niet groter zijn dan de maximumwaarde.");
        }

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int randomValue = random.nextInt(max - min + 1) + min; // Genereer een waarde tussen min en max
            numbers.add(randomValue);
        }
        return numbers;
    }
}
