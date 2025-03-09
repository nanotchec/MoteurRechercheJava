package moteur;

/**
 * Classe Mot représentant un mot individuel dans un document.
 */
public class Mot {
    private String mot;

    /**
     * Constructeur à un argument.
     * @param mot Le mot à représenter.
     * @throws IllegalArgumentException Si le mot est nul ou vide.
     */
    public Mot(String mot) {
        if (mot == null || mot.isEmpty()) {
            throw new IllegalArgumentException("Le mot ne peut pas être nul ou vide.");
        }
        this.mot = mot;
    }

    /**
     * Accesseur pour le mot.
     * @return Le mot représenté.
     */
    public String getMot() {
        return mot;
    }

    /**
     * Retourne une représentation textuelle du mot.
     * @return Le mot en tant que chaîne de caractères.
     */
    @Override
    public String toString() {
        return mot;
    }
}