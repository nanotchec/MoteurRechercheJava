package moteur;

public enum DataSets {
    WIKIPEDIA("Wikipedia"),
    OUVRAGES("Ouvrages");

    private String nom;

    DataSets(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}