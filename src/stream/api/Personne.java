package stream.api;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Personne {
    private String id;
    private Genre genre;
    private Integer taille;

    public Personne(String id, Genre genre, Integer taille){
        this.id = id;
        this.genre = genre;
        this.taille = taille;
    }

    public String getId(){
        return this.id;
    }

    public Integer getTaille(){
        return this.taille;
    }

    public static void main(String[] args) {

        List<Personne> personnes = Arrays.asList(
                new Personne("P1", Genre.HOMME, 176),
                new Personne("P2", Genre.HOMME, 176),
                new Personne("P3", Genre.HOMME, 190),
                new Personne("P4", Genre.FEMME, 172),
                new Personne("P5", Genre.FEMME, 162),
                new Personne("P6", Genre.FEMME, 168));


        // Calculate the average women height
        // Traditional approach
        double sommeTaille = 0;
        int totalFemmes = 0;
        for(Personne personne: personnes){
            if (personne.genre == Genre.FEMME){
                totalFemmes++;
                sommeTaille += personne.taille;
            }
        }
        System.out.println(String.format("La taille moyenne des femmes est %.3f", sommeTaille/totalFemmes));

        // Using Streams
        double tailleMoyenneStream = personnes.stream()
                .filter(personne -> personne.genre == Genre.FEMME)
                .mapToInt(p -> p.taille)
                .average()
                .orElse(0);
        System.out.println(String.format("La taille moyenne des femmes avec stream est %.3f", tailleMoyenneStream));


        // Get masculine persons ids
        personnes.stream()
                .filter(personne -> personne.genre == Genre.HOMME)
                .sorted(Comparator.comparingInt(Personne::getTaille).reversed())
                .map(Personne::getId)
                //.map(personne -> personne.id)
                .forEach(System.out::println);
    }
}
