package moteur;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Document représentant un document dans le corpus.
 */
public class Document {
    private String titre;
    private String contenu;

    /**
     * Constructeur de Document.
     * @param titre Le titre du document.
     * @param contenu Le contenu du document.
     * @throws IllegalArgumentException Si le titre ou le contenu est nul ou vide.
     */
    public Document(String titre, String contenu) {
        if (titre == null || titre.isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être nul ou vide.");
        }
        if (contenu == null || contenu.isEmpty()) {
            throw new IllegalArgumentException("Le contenu ne peut pas être nul ou vide.");
        }
        this.titre = titre;
        this.contenu = contenu;
    }

    // Getters
    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    /**
     * Récupère tous les mots du document.
     * @return Une liste de mots.
     */
    public List<String> getMots() {
        List<String> mots = new ArrayList<>();
        for (String mot : contenu.split("\\s+")) {
            mot = mot.toLowerCase();
            mots.add(mot);
        }
        return mots;
    }

    /**
     * Récupère les mots filtrés selon les critères de Vocabulaire.
     * @return Une liste de mots filtrés.
     */
    public List<String> getMotsFiltres() {
        return getMots();
    }
    
    /*
     * retourne le texte complet d'un document
     */

    @Override
    public String toString() {
        return "Titre du document : " + titre + "\nContenu du document : " + contenu;
    }
}