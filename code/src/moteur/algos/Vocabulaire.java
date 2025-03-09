package moteur.algos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import moteur.Corpus;
import moteur.Document;
import moteur.taille.TailleMot;

/**
 * Classe Vocabulaire pour gérer le vocabulaire unique en excluant les stopwords et les mots filtrés par TailleMot.
 */
public class Vocabulaire {
    private Set<String> motsUniques;
    private HashSet<String> stopList;
    private TailleMot tailleMot;

    /**
     * Constructeur de Vocabulaire.
     * @param corpus Le corpus de documents.
     * @param tailleMot Instance de TailleMot pour filtrer les mots par longueur.
     */
    public Vocabulaire(Corpus corpus, TailleMot tailleMot) {
        if (corpus == null) {
            throw new IllegalArgumentException("Le corpus ne peut pas être nul.");
        }
        if (tailleMot == null) {
            throw new IllegalArgumentException("La classe TailleMot ne peut pas être nulle.");
        }
        this.tailleMot = tailleMot;
        motsUniques = new HashSet<>();
        stopList = new HashSet<>();
        chargerStopWords(); // Charger les stopwords depuis le fichier
        for (Document doc : corpus) {
            motsUniques.addAll(getMotsFiltres(doc));
        }
    }

    /**
     * Méthode pour charger les stopwords depuis le fichier ressources/stopwords.txt.
     */
    private void chargerStopWords() {
        String cheminStopWords = "ressources/stopwords.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(cheminStopWords))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim().toLowerCase();
                if (!ligne.isEmpty()) {
                    stopList.add(ligne);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des stopwords : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Filtre les mots d'un document en excluant les stopwords et les mots filtrés par TailleMot.
     * @param doc Le document à filtrer.
     * @return Un ensemble de mots filtrés.
     */
    private Set<String> getMotsFiltres(Document doc) {
        Set<String> motsFiltres = new HashSet<>();
        for (String mot : doc.getMotsFiltres()) {
            mot = mot.toLowerCase();
            if (!stopList.contains(mot) && tailleMot.estValide(mot)) {
                motsFiltres.add(mot);
            }
        }
        return motsFiltres;
    }

    // Getters
    public HashSet<String> getStopList() {
        return stopList;
    }

    public Set<String> getMotsUniques() {
        return motsUniques;
    }

    public TailleMot getTailleMot() {
        return tailleMot;
    }

   
}