package kwic;

/**
 * Custom Exception for InputProcessor and OutputProcessor.
 */
public class IOProcessingException extends Exception {
    public IOProcessingException(String message) {
        super(message);
    }
}
