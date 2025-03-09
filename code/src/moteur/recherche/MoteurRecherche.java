package moteur.recherche;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import moteur.Corpus;
import moteur.Document;
import moteur.algos.Vocabulaire;
import moteur.exceptions.Bm25Exception;
import moteur.exceptions.MoteurRechercheException;
import moteur.exceptions.TfIdfException;

/**
 * Classe MoteurRecherche pour gérer les recherches dans le corpus.
 */
public class MoteurRecherche {
    private Corpus corpus;
    private Vocabulaire vocabulaire;

    /**
     * Constructeur de MoteurRecherche.
     * @param corpus Le corpus de documents.
     * @throws MoteurRechercheException Si le corpus est nul ou vide.
     */
    public MoteurRecherche(Corpus corpus) throws MoteurRechercheException {
        if (corpus == null || corpus.isEmpty()) {
            throw new MoteurRechercheException("Le corpus ne peut pas être nul ou vide.");
        }
        this.corpus = corpus;
        this.vocabulaire = corpus.getVocabulaire();
    }

    /**
     * Recherche des documents pertinents en utilisant Tf-Idf.
     * @param requete La requête de recherche.
     * @return Une liste de résultats de recherche.
     * @throws TfIdfException Si la requête est nulle ou vide.
     */
    public List<ResultatRecherche> rechercherTfIdf(String requete) throws TfIdfException {
        if (requete == null || requete.trim().isEmpty()) {
            throw new TfIdfException("La requête ne peut pas être nulle ou vide.");
        }

        List<ResultatRecherche> resultats = new ArrayList<>();
        String[] motsRequete = requete.split("\\s+");

        for (Document doc : corpus) {
            double score = 0;
            for (String mot : motsRequete) {
                mot = mot.toLowerCase();
             // Exclusion des stopwords et des mots ne respectant pas la longueur
                if (!vocabulaire.getStopList().contains(mot) && vocabulaire.getTailleMot().estValide(mot)) {
                    score += corpus.getTfIdf(mot, doc);
                }
            }
            if (score > 0) {
                resultats.add(new ResultatRecherche(doc, score));
            }
        }

        // Trier les résultats par score décroissant
        Collections.sort(resultats, Comparator.comparing(ResultatRecherche::getScore).reversed());

        return resultats;
    }

    /**
     * Recherche des documents pertinents en utilisant BM25.
     * @param requete La requête de recherche.
     * @return Une liste de résultats de recherche.
     * @throws Bm25Exception Si la requête est nulle ou vide.
     */
    public List<ResultatRecherche> rechercherBm25(String requete) throws Bm25Exception {
        if (requete == null || requete.trim().isEmpty()) {
            throw new Bm25Exception("La requête ne peut pas être nulle ou vide.");
        }

        List<ResultatRecherche> resultats = new ArrayList<>();
        String[] motsRequete = requete.split("\\s+");

        for (Document doc : corpus) {
            double score = 0;
            for (String mot : motsRequete) {
                mot = mot.toLowerCase();
             // Exclusion des stopwords et des mots ne respectant pas la longueur
                if (!vocabulaire.getStopList().contains(mot) && vocabulaire.getTailleMot().estValide(mot)) {
                    score += corpus.getBm25(mot, doc);
                }
            }
            if (score > 0) {
                resultats.add(new ResultatRecherche(doc, score));
            }
        }

        // Trier les résultats par score décroissant
        Collections.sort(resultats, Comparator.comparing(ResultatRecherche::getScore).reversed());

        return resultats;
    }
}