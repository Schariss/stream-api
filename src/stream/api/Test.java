package stream.api;

import java.util.*;
import java.util.function.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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

//        System.out.println("--------");
//        List<Integer> list = new ArrayList<>();
//        List<Integer> result = Stream.of(1, 2, 3, 4)
//                .peek(x -> list.add(x))
//                .map(x -> x * 2)
//                .collect(Collectors.toList());
//
//        System.out.println(list);
//        System.out.println(result);



        // Map vs flatMap
        System.out.println("------------------ Map");
        List<Integer> numbers = Arrays.asList(1, 3, 5, 7, 9);
        List<List<Integer>> tuples =
                numbers.stream()
                        .map(nombre -> Arrays.asList(nombre - 1, nombre))
                        .collect(Collectors.toList());
        System.out.println(tuples);

        System.out.println("------------------ FlatMap");
        List<Integer> nombresDesTuples =
                numbers.stream()
                        .flatMap(nombre -> Arrays.asList(nombre - 1, nombre).stream())
                        .collect(Collectors.toList());
        System.out.println(nombresDesTuples);



        List<String> prenoms = Arrays.asList("andre", "benoit", "albert", "thierry", "alain");
        Supplier<Stream<String>> prenomsStream = () -> prenoms.stream();

        System.out.println("------------------ Sorted");
        prenomsStream.get().sorted().forEach(System.out::println);

        System.out.println("------------------ Supplier");
        prenomsStream.get().filter(p -> p.startsWith("a")).sorted().forEach(System.out::println);



        System.out.println("------------------ forEach() and forEachOrdered()");
        Consumer<String> afficherElement = s -> System.out.println(s + " - " +
                Thread.currentThread().getName());
        prenoms.stream().sorted().forEach(afficherElement);
        System.out.println();
        prenoms.parallelStream().sorted().forEach(afficherElement);
        System.out.println();
        prenoms.parallelStream().sorted().forEachOrdered(afficherElement);



        System.out.println("\n------------------ collect() Method");
        List<String> elements = Arrays.asList("elem1", "elem2", "elem2", "elem3", "elem4");
        // <R> R collect(Supplier<R> resultSupplier, BiConsumer<R, T> accumulator, BiConsumer<R, R> combiner)
        // supplier : provide an instance of the empty container
        // accumulator : add an element in the container
        // combiner : combine two containers when using the parallel stream
        Set<String> ensemble = elements.stream()
                .collect(() -> new LinkedHashSet<>(),
                        (s, e) -> s.add(e),
                        (s1, s2) -> s1.addAll(s2));
        // Or
        // Set<String> ensemble = elements.stream()
        // .collect(LinkedHashSet::new,
        //      LinkedHashSet::add,
        //      LinkedHashSet::addAll);
        System.out.println(ensemble);

        System.out.println("------------------ BiConsumer()");
        BiConsumer<Boolean, Integer> isEven = (is, x) -> {
            if(is){
                System.out.println(String.format("Le nombre %d est paire", x));
            }else
                System.out.println(String.format("Le nombre %d est impaire", x));
        };
        Integer x = 8;
        isEven.accept(x % 2 == 0, x);

        System.out.println("------------------ StringBuilder");
        StringBuilder elmtSb =
                elements.stream()
                        .collect(() -> new StringBuilder(),
                                (sb, s) -> {
                                    if (sb.length() != 0) {
                                        sb.append(";");
                                    }
                                    sb.append(s);
                                },
                                (sb1, sb2) -> sb1.append(";").append(sb2));
        System.out.println(elmtSb);

        System.out.println("------------------ StringJoiner");
        StringJoiner elmtJoiner =
                elements.stream()
                        .collect(() -> new StringJoiner(";"),
                                (j, e) -> j.add(e),
                                (j1, j2) -> j1.merge(j2));
        System.out.println(elmtJoiner.toString());

        System.out.println("------------------ joining from Collectors");
        System.out.println(elements.stream().collect(Collectors.joining(";")));


        System.out.println("------------------ reduce");
        List<Personne> personnes = new Departement("Mathématiques").getPersonnes();
        personnes.stream()
//                .mapToInt(Personne::getTaille)
//                .reduce((p1, p2) -> p1 > p2? p1: p2)
                .reduce((p1, p2) -> p1.getTaille() > p2.getTaille()? p1: p2)
                .ifPresent(System.out::println);

        System.out.println("------------------ reduce using BinaryOperator");
        Comparator<Personne> comparerTaille = Comparator.comparingInt(Personne::getTaille);
        BinaryOperator<Personne> tailleMax = BinaryOperator.maxBy(comparerTaille);
        personnes.stream().reduce(tailleMax).ifPresent(System.out::println);

        System.out.println("------------------ reduce concat Strings");
        Stream.of("a", "b", "c", "d")
                .reduce((accumulator, item) -> accumulator + item)
                .ifPresent(System.out::println);

        System.out.println("------------------ reduce max int");
        System.out.println("------------------ M1");
        System.out.println(Stream.of(1, 2, 3, 4, 5)
                .mapToInt(i -> i)
                .max());
        System.out.println("------------------ M2");
        System.out.println(Stream.of(1, 2, 3, 4, 5)
                .reduce((i1, i2) -> i1 < i2? i2: i1));
        System.out.println("------------------ M3");
        System.out.println(Stream.of(1, 2, 3, 4, 5)
                .reduce((i1, i2) -> i1 < i2? i2: i1));
        System.out.println("------------------ M4");
        System.out.println(Stream.of(1, 2, 3, 4, 5)
                .reduce(0, Integer::max));

        System.out.println("------------------ reduce");
        // <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
        Integer tailleTotale = personnes.stream()
                .reduce(0, (somme, e) -> somme += e.getTaille(),
                        (somme1, somme2) -> somme1 + somme2);
        System.out.println("Taille totale = " + tailleTotale);

        System.out.println("------------------ Lambda functions");
        // The code above is the same as the following
        BiFunction<Integer, Personne, Integer> accumulator = (somme, e) -> somme += e.getTaille();
        Integer resultat = 0;
        for (Personne e : personnes) {
            resultat = accumulator.apply(resultat, e);
        }
        System.out.println("Taille totale = " + tailleTotale);



        System.out.println("------------------ min() and max()");
        Optional<String> plusPetitPrenom =
                prenoms.stream().min(Comparator.comparing(element -> element.length()));
        // Same as; using Method reference
        // Optional<String> plusPetitPrenom =
        //                prenoms.stream().min(Comparator.comparing(element -> element.length()));
        System.out.println(plusPetitPrenom.orElseGet(() -> "aucun prenom trouve"));



        System.out.println("------------------ iterator");
        Iterator<String> prenomsIterator = prenomsStream.get().iterator();
        while(prenomsIterator.hasNext()){
            System.out.println(prenomsIterator.next());
        }



        System.out.println("------------------ Function example");
        Function<String, Integer> calculateLength = (word) -> word.length();
        System.out.println("La longueur du mot est " + calculateLength.apply("Adnane"));

        System.out.println("------------------ toMap from Collectors");
        // mergeFunction tu deal with duplicate keys
        Map<Integer, String> resultats =
                elements.stream().collect(Collectors.toMap(String::length,
                        Function.identity(),
                        (s1, s2) -> String.join(";", s1, s2)));
        System.out.println(resultats);
    }
}
