package moteur.recherche;

import moteur.Document;

/**
 * Classe ResultatRecherche représentant un résultat de recherche avec un document et un score.
 */
public class ResultatRecherche {
    private Document document;
    private double score;

    /**
     * Constructeur de ResultatRecherche.
     * @param document Le document trouvé.
     * @param score Le score de pertinence.
     */
    public ResultatRecherche(Document document, double score) {
        this.document = document;
        this.score = score;
    }

    // Getters
    
    /**
     * Retourne le document associé au résultat de recherche.
     * @return Le document trouvé.
     */
    public Document getDocument() {
        return document;
    }
    
    /**
     * Retourne le score de pertinence du document.
     * @return Le score de pertinence.
     */

    public double getScore() {
        return score;
    }
}