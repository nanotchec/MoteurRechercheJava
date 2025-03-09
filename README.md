Moteur de Recherche en Java

✨ Introduction

Ce projet est un moteur de recherche en Java permettant d'effectuer des recherches dans un corpus de documents en utilisant TF-IDF et BM25, deux algorithmes classiques d'évaluation de pertinence en recherche d'information.

Le projet charge un corpus de documents textuels, construit un vocabulaire en excluant les mots peu informatifs (stopwords) et permet d'effectuer des recherches par mots-clés pour obtenir les documents les plus pertinents.

⚙️ Fonctionnalités

Chargement d'un corpus de documents depuis un fichier.

Suppression des mots peu informatifs (stopwords) 📄.

Calcul de la pertinence des documents avec TF-IDF et BM25.

Affichage des 10 meilleurs résultats triés par pertinence.

Gestion des exceptions en cas de fichiers absents, corpus vide, requête incorrecte.

Interface en ligne de commande pour tester des requêtes utilisateur.

🛠 Installation

Prérequis

Java 8 ou supérieur doit être installé.

Un IDE comme IntelliJ IDEA, Eclipse, ou VS Code (optionnel).

Git pour cloner le dépôt (ou téléchargement manuel).

Cloner le projet

# Via GitHub
git clone https://github.com/<ton_nom_utilisateur>/MoteurRechercheJava.git
cd MoteurRechercheJava

Compiler et Exécuter

💻 Avec un terminal (sans IDE) :

javac -d bin -sourcepath src src/moteur/Test.java
java -cp bin moteur.Test

🎮 Avec IntelliJ IDEA ou Eclipse :

Importer le projet.

Compiler et exécuter la classe moteur.Test.

🔍 Exécution et Exemple d'Utilisation

Une fois le programme lancé, entrez une requête :

Entrez votre requête (tapez 'exit' pour quitter) :
fantasy dragon

Le moteur affiche les 10 documents les plus pertinents avec leurs scores :

Résultats de recherche avec Tf-Idf :
1. Titre : The Dragon Rider | Score : 0.4823
2. Titre : A Song of Ice and Fire | Score : 0.4502
...

Résultats de recherche avec BM25 :
1. Titre : Eragon | Score : 7.542
2. Titre : The Hobbit | Score : 7.315
...

Pour quitter, tapez exit.

⚖️ Structure du Projet

MoteurRechercheJava/
├── src/
|   ├── moteur/
|   |   ├── Corpus.java          # Gestion du corpus de documents
|   |   ├── Document.java        # Représentation d'un document
|   |   ├── Mot.java             # Représentation d'un mot
|   |   ├── Test.java            # Programme principal (interface CLI)
|   |   ├── exceptions/         # Gestion des erreurs
|   |   ├── recherche/          # Gestion des recherches (BM25, Tf-Idf)
|   |   ├── algos/              # Algorithmes de calcul
|   |   ├── taille/             # Filtres sur les mots/documents
├── ressources/
|   ├── booksummaries.txt    # Corpus de documents
|   ├── stopwords.txt        # Liste des mots à exclure
├── README.md

🤖 Fonctionnement du Moteur

Chargement du corpus (Corpus.java)

Lit le fichier booksummaries.txt.

Stocke les documents dans une liste.

Traitement du vocabulaire (Vocabulaire.java)

Exclut les stopwords.

Filtre les mots selon leur longueur.

Calcul des scores

Tf-Idf (TfIdf.java) : Mesure l'importance d'un mot dans un document.

BM25 (Bm25.java) : Algorithme plus avancé qui prend en compte la longueur du document et la fréquence des mots.

Recherche et tri des documents (MoteurRecherche.java)

Compare les mots-clés de la requête avec les mots des documents.

Trie les documents par pertinence.

