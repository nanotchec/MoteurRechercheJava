package moteur.exceptions;

/**
 * Classe Bm25Exception pour les exceptions liées au calcul de BM25.
 */
public class Bm25Exception extends MoteurRechercheException {

    /**
     * Constructeur avec message.
     * @param message Le message de l'exception.
     */
    public Bm25Exception(String message) {
        super(message);
    }
}
