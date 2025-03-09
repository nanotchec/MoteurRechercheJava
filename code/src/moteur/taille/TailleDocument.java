package moteur.taille;

import moteur.Corpus;

public class TailleDocument {
	/**
     * Méthode calculer ayant comme paramètre un objet Corpus et retournant un entier.
     * @param corpus Le corpus de documents.
     * @return Le nombre de documents dans le corpus.
     * @throws IllegalArgumentException Si le corpus est nul.
     */    public int calculer(Corpus corpus) {
        if (corpus == null) {
            throw new IllegalArgumentException("Le corpus ne peut pas être nul.");
        }
        return corpus.size();
    }
}