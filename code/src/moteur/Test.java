package moteur;

import java.util.List;
import java.util.Scanner;

import moteur.exceptions.Bm25Exception;
import moteur.exceptions.CorpusException;
import moteur.exceptions.MoteurRechercheException;
import moteur.exceptions.TfIdfException;
import moteur.recherche.MoteurRecherche;
import moteur.recherche.ResultatRecherche;

/**
 * Classe Test pour tester les fonctionnalités de recherche.
 */
public class Test {
    public static void main(String[] args) {
        String cheminFichier = "ressources/booksummaries.txt";
        int maxResultats = 10; // Nombre maximum de résultats à afficher

        try {
            /*
            // Test de CorpusException en passant un chemin null
            try {
                Corpus corpusNull = new Corpus(null, DataSets.OUVRAGES);
            } catch (CorpusException e) {
                System.out.println("Test CorpusException avec chemin null :");
                System.out.println("Exception capturée : " + e.getClass().getName());
                System.out.println("Message : " + e.getMessage());
                System.out.println();
            }

            // Test de TfIdfException en passant un corpus vide
            try {
                Corpus corpusVide = new Corpus("fichier_vide.txt", DataSets.OUVRAGES);
                // Supposons que fichier_vide.txt existe mais est vide ou contient zéro document valide
                corpusVide.initialiserTfIdf();
            } catch (CorpusException | TfIdfException e) {
                System.out.println("Test TfIdfException avec corpus vide :");
                System.out.println("Exception capturée : " + e.getClass().getName());
                System.out.println("Message : " + e.getMessage());
                System.out.println();
            }

            // Test de Bm25Exception en passant une requête nulle ou vide
            try {
                Corpus corpus = new Corpus(cheminFichier, DataSets.OUVRAGES);
                corpus.initialiserBm25();
                MoteurRecherche moteur = new MoteurRecherche(corpus);
                moteur.rechercherBm25("");
            } catch (Bm25Exception e) {
                System.out.println("Test Bm25Exception avec requête vide :");
                System.out.println("Exception capturée : " + e.getClass().getName());
                System.out.println("Message : " + e.getMessage());
                System.out.println();
            }
            */

            //code normal de l'app

            // Créer une instance de Corpus
            Corpus corpus = new Corpus(cheminFichier, DataSets.OUVRAGES);

            // Afficher le nombre de documents chargés
            System.out.println("Nombre de documents chargés : " + corpus.size());

            // Initialiser les pondérations
            corpus.initialiserTfIdf();
            corpus.initialiserBm25();

            // Créer le moteur de recherche
            MoteurRecherche moteur = new MoteurRecherche(corpus);

            // Lecture des requêtes saisies au clavier
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Entrez votre requête (tapez 'exit' pour quitter) :");
                String requete = scanner.nextLine().trim();

                if (requete.equalsIgnoreCase("exit")) {
                    break;
                }

                try {
                    // Rechercher avec Tf-Idf
                    List<ResultatRecherche> resultatsTfIdf = moteur.rechercherTfIdf(requete);
                    System.out.println("\nRésultats de recherche avec Tf-Idf :");
                    if (resultatsTfIdf.isEmpty()) {
                        System.out.println("Aucun résultat trouvé.");
                    } else {
                        for (int i = 0; i < Math.min(maxResultats, resultatsTfIdf.size()); i++) {
                            ResultatRecherche res = resultatsTfIdf.get(i);
                            System.out.println((i + 1) + ". Titre : " + res.getDocument().getTitre() + " | Score : " + res.getScore());
                        }
                    }
                } catch (TfIdfException e) {
                    System.out.println("Exception capturée : " + e.getClass().getName());
                    System.out.println("Message : " + e.getMessage());
                }

                try {
                    // Rechercher avec BM25
                    List<ResultatRecherche> resultatsBm25 = moteur.rechercherBm25(requete);
                    System.out.println("\nRésultats de recherche avec BM25 :");
                    if (resultatsBm25.isEmpty()) {
                        System.out.println("Aucun résultat trouvé.");
                    } else {
                        for (int i = 0; i < Math.min(maxResultats, resultatsBm25.size()); i++) {
                            ResultatRecherche res = resultatsBm25.get(i);
                            System.out.println((i + 1) + ". Titre : " + res.getDocument().getTitre() + " | Score : " + res.getScore());
                        }
                    }
                } catch (Bm25Exception e) {
                    System.out.println("Exception capturée : " + e.getClass().getName());
                    System.out.println("Message : " + e.getMessage());
                }

                System.out.println(); // Ligne vide pour la lisibilité
            }
            scanner.close();

        } catch (CorpusException e) {
            System.out.println("Exception capturée : " + e.getClass().getName());
            System.out.println("Message : " + e.getMessage());
        } catch (MoteurRechercheException e) {
            System.out.println("Exception capturée : " + e.getClass().getName());
            System.out.println("Message : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception inattendue capturée : " + e.getClass().getName());
            System.out.println("Message : " + e.getMessage());
            e.printStackTrace();
        }
    }
}