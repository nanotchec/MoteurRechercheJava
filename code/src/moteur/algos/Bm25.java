package moteur.algos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import moteur.Corpus;
import moteur.Document;
import moteur.exceptions.Bm25Exception;

/**
 * Classe Bm25 pour calculer les scores BM25.
 */
public class Bm25 {

    private Corpus corpus;
    private Map<String, Double> idfMap;
    private Vocabulaire vocabulaire;
    private double k1 = 1.5; // Paramètre de saturation de terme
    private double b = 0.75; // Paramètre de prise en compte de la longueur du document
    private double avgDocLength;

    /**
     * Constructeur de Bm25.
     * @param corpus Le corpus de documents.
     * @throws Bm25Exception Si le corpus est nul ou vide.
     */
    public Bm25(Corpus corpus) throws Bm25Exception {
        if (corpus == null || corpus.isEmpty()) {
            throw new Bm25Exception("Le corpus ne peut pas être nul ou vide.");
        }
        this.corpus = corpus;
        this.vocabulaire = corpus.getVocabulaire();
        this.idfMap = calculerIdf();
        this.avgDocLength = calculerAvgDocLength();
    }

    /**
     * Calcule le score BM25 pour un mot dans un document.
     * Utilise les paramètres k1 et b pour ajuster l'influence de la fréquence du terme et de la longueur du document.
     * @param mot Le mot à évaluer.
     * @param doc Le document concerné.
     * @return Le score BM25.
     */
    public double calculerBm25(String mot, Document doc) {
        mot = mot.toLowerCase();
     // Exclusion des mots de la liste de stopwords
        if (vocabulaire.getStopList().contains(mot)) {
            return 0.0;
        }
        double idf = idfMap.getOrDefault(mot, 0.0);
        double tf = calculerTf(mot, doc);
        double docLength = doc.getMotsFiltres().size();
        // Calcul du score BM25 en utilisant la formule standard

        double score = idf * ((tf * (k1 + 1)) / (tf + k1 * (1 - b + b * (docLength / avgDocLength))));
        return score;
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
        return count;
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
            double idf = Math.log((double) (totalDocs - docFreq + 0.5) / (docFreq + 0.5));
            idfMap.put(mot, idf);
        }
        return idfMap;
    }

    /**
     * Calcule la longueur moyenne des documents du corpus.
     * @return La longueur moyenne des documents.
     */
    private double calculerAvgDocLength() {
        double totalLength = 0;
        for (Document doc : corpus) {
            totalLength += doc.getMotsFiltres().size();
        }
        return totalLength / corpus.size();
    }
}