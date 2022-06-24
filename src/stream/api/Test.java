package stream.api;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> entiers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        // Sum of numbers less than 10
        // First Method
        long sommeM1 = entiers.stream()
                .filter(v -> v < 10)
                        .reduce((fstnb, secnb) -> fstnb + secnb).get();
        System.out.println(sommeM1);

        // Second Method
        long sommeM2 = entiers.stream()
                .filter(v -> v < 10)
                .mapToInt(i -> i)
                .sum();
        System.out.println(sommeM2);

        // Third Method
        long sommeM3 = entiers.parallelStream()
                .filter(v -> v < 10)
                .mapToInt(i -> i)
                .sum();
        System.out.println(sommeM3);
    }
}
