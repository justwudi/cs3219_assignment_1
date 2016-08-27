package wudi;

import java.util.HashSet;

/**
 * Master Control for the KWIC (Key Word In Context) index system.
 *
 * This is implemented with the Abstract Data Types architecture.
 */
public class Controller {

    private static String getInputPath(ArgParser.ParsedArguments parsedArguments) {
        return parsedArguments.getArgument("input");
    }

    private static String getOutputPath(ArgParser.ParsedArguments parsedArguments) {
        String outputFilePath = "./output.txt";

        if (parsedArguments.hasArgument("output")) {
            outputFilePath = parsedArguments.getArgument("output");
        }

        return outputFilePath;
    }

    private static String getIgnoredWordsPath(ArgParser.ParsedArguments parsedArguments) {
        String ignoreWordsFilePath = null;

        if (parsedArguments.hasArgument("ignore")) {
            ignoreWordsFilePath = parsedArguments.getArgument("ignore");
        }

        return ignoreWordsFilePath;
    }

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

    public static void main(String[] args) {
        ArgParser parser = new ArgParser();
        parser.registerArgument("input", "i", true, 1, "path of input file");
        parser.registerArgument("output", "o", false, 1, "path of output file");
        parser.registerArgument("ignore", "g", false, 1, "path of words-to-ignore file");

        ArgParser.ParsedArguments parsedArguments = parser.parse(args);
        String inputFilePath = getInputPath(parsedArguments);
        String outputFilePath = getOutputPath(parsedArguments);
        String ignoreWordsFilePath = getIgnoredWordsPath(parsedArguments);

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
