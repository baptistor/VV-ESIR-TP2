# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

A refresher on TCC and LCC is available in the [course notes](https://oscarlvp.github.io/vandv-classes/#cohesion-graph).

## Answer

public class Losange {

    // Attributs
    private double diagonale1;  // Longueur de la première diagonale
    private double diagonale2;  // Longueur de la deuxième diagonale

    // Constructeur
    public Losange(double diagonale1, double diagonale2) {
        this.diagonale1 = diagonale1;
        this.diagonale2 = diagonale2;
    }

    public double aire() {
        return (diagonale1 * diagonale2) / 2;
    }

    public double perimetre() {
        // Calcul des côtés via la géométrie du losange
        double cote = Math.sqrt(Math.pow(diagonale1 / 2, 2) + Math.pow(diagonale2 / 2, 2));
        return 4 * cote;
    }

    public void setDiagonale1(double diagonale1) {
        this.diagonale1 = diagonale1;
    }

    public void setDiagonale2(double diagonale2) {
        this.diagonale2 = diagonale2;
    }
}

Le TCC (Tight Class Cohesion) mesure la proportion de paires de méthodes directement connectées par des attributs partagés dans une classe, tandis que le LCC (Loose Class Cohesion) inclut aussi les connections transitives donc indirectes, via des attributs ou d'autres méthodes.
On remarque que dans la classe Losange, chaque méthode utilise au moins un attribut commun avec une autre méthode. Par exemple, perimetre() et setDiagonale1 utilisent toutes les deux l'attribut diagonale1. Comme toutes les méthodes sont directement connectées, le TCC et le LCC auront la même valeur dans cette classe. 
Logiquement, le LCC ne peut pas être inférieur au TCC car le calcul du LCC est basé sur le nombre de connexion direct (TCC) + le nombre de connexion par transitivité.


