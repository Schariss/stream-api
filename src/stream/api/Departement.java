package stream.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Departement {
    private String nom;
    private List<Personne> personnes = new ArrayList<>();

    public Departement(String nom, List<Personne> personnes){
        this.nom = nom;
        this.personnes = personnes;
    }

    public String getNom(){
        return this.nom;
    }

    public List<Personne> getPersonnes(){
        return this.personnes;
    }

    public static void main(String[] args) {
        List<Personne> personnesAvecNomHommes = new ArrayList<>(Arrays.asList(
                new Personne("P1", "Adnane", Genre.HOMME, 176),
                new Personne("P2", "Aimad", Genre.HOMME, 176),
                new Personne("P3", "Aissam", Genre.HOMME, 190)));

        List<Personne> personnesAvecNomFemmes = Arrays.asList(
                new Personne("P4", "Imane", Genre.FEMME, 172),
                new Personne("P5", "Widad", Genre.FEMME, 162),
                new Personne("P6", "Amina", Genre.FEMME, 168));

        List<Departement> departements = Arrays.asList(
                new Departement("Mathematiques", personnesAvecNomHommes),
                new Departement("Physiques", personnesAvecNomFemmes)
        );

        // FlatMap example
        System.out.println("------ FlatMap");
        List<Personne> personnesDepartementsFlatMap = departements.stream()
                .flatMap(departement -> departement.getPersonnes().stream())
                .collect(Collectors.toList());
        personnesDepartementsFlatMap.forEach(System.out::println);

        // map example
        System.out.println("------ MAP");
        List<List<Personne>> personnesDepartementsMap = departements.stream()
                .map(departement -> departement.getPersonnes())
                .collect(Collectors.toList());
        personnesDepartementsMap.forEach(System.out::println);


        System.out.println("------ Distinct");
        Personne p1 = new Personne("P1", "Adnane", Genre.HOMME, 178);
        Personne p1bis = new Personne("P1", "Adnane", Genre.HOMME, 176);
        System.out.println(p1 == p1bis);
        System.out.println(p1.equals(p1bis));
        personnesAvecNomHommes.add(p1bis);
        System.out.println(p1.hashCode());
        System.out.println(p1bis.hashCode());
        System.out.println(
                personnesAvecNomHommes.stream()
                        .distinct().collect(Collectors.toList()));
    }
}
