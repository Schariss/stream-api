package stream.api;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Personne {
    private String id;
    private Genre genre;
    private Integer taille;
    private String nom;

    public Personne(String id, Genre genre, Integer taille) {
        this.id = id;
        this.genre = genre;
        this.taille = taille;
    }

    public Personne(String id, String nom, Genre genre, Integer taille) {
        this.id = id;
        this.genre = genre;
        this.taille = taille;
        this.nom = nom;
    }

    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true
        if (obj == this)
            return true;
        if(!this.id.equals(((Personne) obj).getId())
                || !this.nom.equals(((Personne) obj).getNom())
                // Same Person but height has changed
                // || this.taille.intValue() != ((Personne) obj).getTaille().intValue()
                || this.genre != ((Personne) obj).getGenre()
        )
            return false;
        if(obj == null)
            return false;
        if(obj.getClass() != this.getClass())
            return false;
        return true;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((genre == null) ? 0 : genre.toString().hashCode());
        return result;
    }

    public String getId() {
        return this.id;
    }

    public Integer getTaille() {
        return this.taille;
    }
    public Genre getGenre() {
        return this.genre;
    }

    public String getNom() {
        return this.nom;
    }

    public String toString() {
        return String.format("Personne[ id = %s, nom = %s, taille = %d, genre = %s]",
                this.id, this.nom, this.taille, this.genre.toString().toLowerCase());
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
        System.out.println("--------");
        double sommeTaille = 0;
        int totalFemmes = 0;
        for (Personne personne : personnes) {
            if (personne.genre == Genre.FEMME) {
                totalFemmes++;
                sommeTaille += personne.taille;
            }
        }
        System.out.println(String.format("La taille moyenne des femmes est %.3f", sommeTaille / totalFemmes));

        // Using Streams
        System.out.println("--------");
        double tailleMoyenneStream = personnes.stream()
                .filter(personne -> personne.genre == Genre.FEMME)
                .mapToInt(p -> p.taille)
                .average()
                .orElse(0);
        System.out.println(String.format("La taille moyenne des femmes avec stream est %.3f", tailleMoyenneStream));


        // Get masculine persons ids
        System.out.println("--------");
        personnes.stream()
                .filter(personne -> personne.genre == Genre.HOMME)
                .sorted(Comparator.comparingInt(Personne::getTaille).reversed())
                .map(Personne::getId)
                //.map(personne -> personne.id)
                .forEach(System.out::println);


        // Get first 5 persons whose name starts with an 'A'
        System.out.println("--------");
        List<Personne> personnesAvecNom = Arrays.asList(
                new Personne("P1", "Adnane", Genre.HOMME, 176),
                new Personne("P2", "Aimad", Genre.HOMME, 176),
                new Personne("P3", "Aissam", Genre.HOMME, 190),
                new Personne("P4", "Imane", Genre.FEMME, 172),
                new Personne("P5", "Widad", Genre.FEMME, 162),
                new Personne("P6", "Amina", Genre.FEMME, 168));
        personnesAvecNom.stream()
                .filter(p -> p.nom.startsWith("A"))
                .limit(5)
                .forEach(System.out::println);

        List<String> prenomsCommencantParA = personnesAvecNom
                .stream()
                .map(Personne::getNom)
                .filter(nom->nom.startsWith("A"))
                .limit(5)
                .collect(Collectors.toList());
        System.out.println(prenomsCommencantParA);
    }
}
