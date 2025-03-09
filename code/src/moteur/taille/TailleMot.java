package moteur.taille;

/**
 * Classe TailleMot pour filtrer les mots en fonction de leur longueur.
 */
public class TailleMot {
    private int minLength;
    private int maxLength;

    /**
     * Constructeur de TailleMot.
     * @param minLength Longueur minimale des mots à conserver.
     * @param maxLength Longueur maximale des mots à conserver.
     * @throws IllegalArgumentException Si les longueurs sont invalides (minLength < 1 ou maxLength < minLength).
     */
    public TailleMot(int minLength, int maxLength) {
        if (minLength < 1 || maxLength < minLength) {
            throw new IllegalArgumentException("Les longueurs doivent être positives et maxLength >= minLength.");
        }
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    /**
     * Vérifie si un mot est valide en fonction de sa longueur.
     * @param mot Le mot à vérifier.
     * @return true si le mot est valide, false sinon.
     */
    public boolean estValide(String mot) {
        if (mot == null) return false;
        int longueur = mot.length();
        return longueur >= minLength && longueur <= maxLength;
    }

    // Getters et setters 
    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        if (minLength < 1) {
            throw new IllegalArgumentException("minLength doit être positif.");
        }
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        if (maxLength < minLength) {
            throw new IllegalArgumentException("maxLength doit être >= minLength.");
        }
        this.maxLength = maxLength;
    }
}