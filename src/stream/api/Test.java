package stream.api;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        List<Integer> entiers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        // Sum of numbers less than 10
        // First Method
        System.out.println("------ M1");
        long sommeM1 = entiers.stream()
                .filter(v -> v < 10)
                        .reduce((fstnb, secnb) -> fstnb + secnb).get();
        System.out.println(sommeM1);

        // Second Method
        System.out.println("------ M2");
        long sommeM2 = entiers.stream()
                .filter(v -> v < 10)
                .mapToInt(i -> i)
                .sum();
        System.out.println(sommeM2);

        // Third Method
        System.out.println("------ M3");
        long sommeM3 = entiers.parallelStream()
                .filter(v -> v < 10)
                .mapToInt(i -> i)
                .sum();
        System.out.println(sommeM3);


        System.out.println("------");
        List<String> caracteresList = Arrays.asList("a1", "a2", "b2", "b1", "c1");
        caracteresList.stream()
                .filter(c -> c.startsWith("b"))
                .sorted()
                .map(String::toUpperCase)
                .forEach(System.out::println);


        // trois premier nombres pairs au carre
        System.out.println("------");
        List<Integer> nombres = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16);
        nombres.stream()
                .filter(nombre -> nombre % 2 == 0)
                .map(nombre -> (int) Math.pow(nombre, 2))
                .limit(7)
                .forEach(System.out::println);


        // Obtaining a Stream
        // From a collection : stream() or parallelStream()
        Stream<String> chaines1 = Arrays.asList("a1", "a2", "a3").stream();

        //From an Array
        //String[] valeurs = new String[] {"a1", "a2", "a3"};
        //Stream<String> chaines2 = Stream.of(valeurs);
        // or
        //chaines2 = Arrays.stream(valeurs);
        //chaines2.forEach(System.out::println);

        // The integer values included between the lower and upper bound excluded
        //IntStream.range(1, 5).forEach(System.out::println);

        // Lines from a file
        // Stream<String> BufferedReader.lines()  or  Stream<String> Files.lines

        //String str = "chaine1,chaine2,chaine3";
        //Pattern.compile(",").splitAsStream(str).forEach(System.out::println);
    }
}
