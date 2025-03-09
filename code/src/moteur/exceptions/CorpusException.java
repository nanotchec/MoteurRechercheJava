package moteur.exceptions;

/**
 * Classe CorpusException pour les exceptions liées au corpus.
 */
public class CorpusException extends MoteurRechercheException {

    /**
     * Constructeur avec message.
     * @param message Le message de l'exception.
     */
    public CorpusException(String message) {
        super(message);
    }
}