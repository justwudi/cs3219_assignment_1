package wudi;

/**
 * Custom Exception for InputProcessor and OutputProcessor.
 */
class IOProcessingException extends Exception {
    IOProcessingException(String message) {
        super(message);
    }
}
