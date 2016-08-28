package wudi;

import java.util.HashSet;

/**
 * Master Control for the KWIC (Key Word In Context) index system.
 *
 * This is implemented with the Abstract Data Types architecture.
 */
public class ADTController {

    private static HashSet<String> getIgnoreWords(InputProcessor inputProcessor, String ignoreWordsFilePath) throws IOProcessingException {
        HashSet<String> ignoredWords = new HashSet<>();

        if (ignoreWordsFilePath != null) {
            LineStorage lineStorage = inputProcessor.processFile(ignoreWordsFilePath);

            for (String word : lineStorage) {
                ignoredWords.add(word);
            }
        }

        return ignoredWords;
    }

    public void generateKWIC(String inputFilePath, String outputFilePath, String ignoreWordsFilePath) {
        InputProcessor inputProcessor = new InputProcessor();
        OutputProcessor outputProcessor = new OutputProcessor();
        LineStorage lineStorage;
        HashSet<String> ignoredWords;
        CircularShifter circularShifter = new CircularShifter();
        Alphabetizer alphabetizer = new Alphabetizer();

        try {
            boolean canOutput = outputProcessor.setOutputFile(outputFilePath);
            if (!canOutput) return;
            lineStorage = inputProcessor.processFile(inputFilePath);
            ignoredWords = getIgnoreWords(inputProcessor, ignoreWordsFilePath);
            circularShifter.setup(lineStorage, ignoredWords);
            alphabetizer.alph(circularShifter);
            outputProcessor.processFile(alphabetizer);
        } catch (IOProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
