package moteur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import moteur.algos.Vocabulaire;
import moteur.algos.TfIdf;
import moteur.algos.Bm25;
import moteur.exceptions.CorpusException;
import moteur.exceptions.TfIdfException;
import moteur.exceptions.Bm25Exception;
import moteur.taille.TailleMot;

/**
 * Classe Corpus pour gérer l'ensemble des documents.
 */
public class Corpus extends Vector<Document> {
    private String titre;
    private DataSets dataset;
    private Vocabulaire vocabulaire;
    private TfIdf tfidf;
    private Bm25 bm25;

    /**
     * Constructeur Corpus.
     * @param cheminFichier Chemin vers le fichier de résumés de livres.
     * @param dataset Le dataset utilisé.
     * @throws CorpusException Si le fichier est nul ou s'il y a une erreur lors du chargement.
     */
    public Corpus(String cheminFichier, DataSets dataset) throws CorpusException {
        if (cheminFichier == null || cheminFichier.isEmpty()) {
            throw new CorpusException("Le chemin du fichier ne peut pas être nul ou vide.");
        }
        this.dataset = dataset;
        this.titre = dataset.toString();
        chargerCorpus(cheminFichier);

        // paramètres de TailleMot min=3, max=20
        TailleMot tailleMot = new TailleMot(3, 20);
        this.vocabulaire = new Vocabulaire(this, tailleMot); // Initialiser vocabulaire avec TailleMot
    }

    /**
     * Charge le corpus à partir du fichier spécifié.
     * @param cheminFichier Chemin vers le fichier.
     * @throws CorpusException Si une erreur survient lors du chargement du corpus.
     */
    private void chargerCorpus(String cheminFichier) throws CorpusException {
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            int numeroLigne = 0;
            while ((ligne = br.readLine()) != null) {
                numeroLigne++;
                String[] parties = ligne.split("\t");
                if (parties.length >= 7) { // Assure qu'il y a au moins 7 champs
                    String titreDoc = parties[2].trim();    // parts[2] est le Titre
                    String contenuDoc = parties[6].trim();  // parts[6] est le Contenu
                    if (!contenuDoc.isEmpty()) {
                        Document doc = new Document(titreDoc, contenuDoc);
                        this.add(doc);
                    } else {
                        System.out.println("Ligne " + numeroLigne + " : contenuDoc est vide. Ligne sautée.");
                    }
                } else {
                    System.out.println("Ligne " + numeroLigne + " : champs insuffisants (" + parties.length + "). Ligne sautée.");
                }
            }
        } catch (Exception e) {
            throw new CorpusException("Erreur lors du chargement du corpus : " + e.getMessage());
        }
    }

    // Getters
    public String getTitre() {
        return titre;
    }

    public DataSets getDataset() {
        return dataset;
    }

    public Vocabulaire getVocabulaire() {
        return vocabulaire;
    }

    /**
     * Initialise le calcul de Tf-Idf.
     * Crée une instance de TfIdf en passant le corpus courant.
     * @throws TfIdfException Si une erreur survient lors de l'initialisation.
     */
    public void initialiserTfIdf() throws TfIdfException {
        this.tfidf = new TfIdf(this);
    }

    /**
     * Récupère le score Tf-Idf pour un mot et un document.
     * Utilise l'instance de TfIdf initialisée pour calculer le score.
     * @param mot Le mot à rechercher.
     * @param doc Le document concerné.
     * @return Le score Tf-Idf.
     * @throws TfIdfException Si TfIdf n'est pas initialisé.
     */
    public double getTfIdf(String mot, Document doc) throws TfIdfException {
        if (tfidf == null) {
            throw new TfIdfException("TfIdf n'a pas été initialisé.");
        }
        return tfidf.calculerTfIdf(mot, doc);
    }

    /**
     * Initialise le calcul de BM25.
     * @throws Bm25Exception Si une erreur survient lors de l'initialisation.
     */
    public void initialiserBm25() throws Bm25Exception {
        this.bm25 = new Bm25(this);
    }

    /**
     * Récupère le score BM25 pour un mot et un document.
     * @param mot Le mot à rechercher.
     * @param doc Le document concerné.
     * @return Le score BM25.
     * @throws Bm25Exception Si BM25 n'est pas initialisé.
     */
    public double getBm25(String mot, Document doc) throws Bm25Exception {
        if (bm25 == null) {
            throw new Bm25Exception("BM25 n'a pas été initialisé.");
        }
        return bm25.calculerBm25(mot, doc);
    }
    /**
     * Retourne une représentation textuelle complète du corpus,
     * incluant le titre, le nombre de documents et les détails de chaque document.
     * @return Une chaîne de caractères représentant le corpus.
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titre du corpus : ").append(titre).append("\n");
        sb.append("Nombre de documents : ").append(this.size()).append("\n");
        sb.append("Documents du corpus :\n");
        for (Document doc : this) {
            sb.append(doc.toString()).append("\n");
        }
        return sb.toString();
    }
}