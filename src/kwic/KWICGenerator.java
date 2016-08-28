package kwic;

/**
 * Interface for KWIC generators.
 */
public interface KWICGenerator {
    void generateKWIC(String inputFilePath, String outputFilePath, String ignoreWordsFilePath);
}
