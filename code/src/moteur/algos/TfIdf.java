package moteur.algos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import moteur.Corpus;
import moteur.Document;
import moteur.exceptions.TfIdfException;

/**
 * Classe TfIdf pour calculer les scores Tf-Idf.
 */
public class TfIdf {

    private Corpus corpus;
    private Map<String, Double> idfMap;
    private Vocabulaire vocabulaire;

    /**
     * Constructeur de TfIdf.
     * @param corpus Le corpus de documents.
     * @throws TfIdfException Si le corpus est nul ou vide.
     */
    public TfIdf(Corpus corpus) throws TfIdfException {
        if (corpus == null || corpus.isEmpty()) {
            throw new TfIdfException("Le corpus ne peut pas être nul ou vide.");
        }
        this.corpus = corpus;
        this.vocabulaire = corpus.getVocabulaire();
        this.idfMap = calculerIdf();
    }

    /**
     * Calcule le score Tf-Idf pour un mot dans un document.
     * @param mot Le mot à évaluer.
     * @param doc Le document concerné.
     * @return Le score Tf-Idf.
     */
    public double calculerTfIdf(String mot, Document doc) {
        mot = mot.toLowerCase();
     // Exclusion des mots de la liste de stopwords
        if (vocabulaire.getStopList().contains(mot)) {
            return 0.0;
        }
        double tf = calculerTf(mot, doc);
        double idf = idfMap.getOrDefault(mot, 0.0);
        return tf * idf;
    }

    /**
     * Calcule la fréquence du terme (TF) dans un document.
     * @param mot Le mot à évaluer.
     * @param doc Le document concerné.
     * @return La fréquence du terme.
     */
    private double calculerTf(String mot, Document doc) {
        List<String> mots = doc.getMotsFiltres();
        double count = 0;
        for (String m : mots) {
            if (m.equals(mot)) {
                count++;
            }
        }
        if (mots.size() == 0) return 0.0;
        return count / mots.size();
    }

    /**
     * Calcule l'IDF pour tous les mots du corpus.
     * @return Une map des mots et leurs IDF.
     */
    private Map<String, Double> calculerIdf() {
        Map<String, Integer> docCount = new HashMap<>();
        int totalDocs = corpus.size();

        // Compter le nombre de documents contenant chaque mot
        for (Document doc : corpus) {
            List<String> motsDoc = doc.getMotsFiltres();
            HashSet<String> motsUniques = new HashSet<>(motsDoc);
            for (String mot : motsUniques) {
                mot = mot.toLowerCase();
                if (!vocabulaire.getStopList().contains(mot) && vocabulaire.getTailleMot().estValide(mot)) {
                    docCount.put(mot, docCount.getOrDefault(mot, 0) + 1);
                }
            }
        }

        // Calculer l'IDF pour chaque mot
        Map<String, Double> idfMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : docCount.entrySet()) {
            String mot = entry.getKey();
            int docFreq = entry.getValue();
            double idf = Math.log((double) totalDocs / (1 + docFreq));
            idfMap.put(mot, idf);
        }
        return idfMap;
    }
}