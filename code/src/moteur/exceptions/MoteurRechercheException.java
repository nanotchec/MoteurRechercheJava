package moteur.exceptions;

/**
 * Classe MoteurRechercheException représentant les exceptions de l'application.
 */
public class MoteurRechercheException extends Exception {

    /**
     * Constructeur par défaut.
     */
    public MoteurRechercheException() {
        super();
    }

    /**
     * Constructeur avec message.
     * @param message Le message de l'exception.
     */
    public MoteurRechercheException(String message) {
        super(message);
    }
}