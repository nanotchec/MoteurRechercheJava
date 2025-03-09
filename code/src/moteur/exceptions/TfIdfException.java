package moteur.exceptions;
/**
 * Classe TfIdfException pour les exceptions liées au calcul de Tf-Idf.
 */
public class TfIdfException extends MoteurRechercheException {

    /**
     * Constructeur avec message.
     * @param message Le message de l'exception.
     */
    public TfIdfException(String message) {
        super(message);
    }
}