import wudi.ADTController;

/**
 * Created by WD on 29/8/16.
 */
public class KWIC {

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

    public static void main(String[] args) {
        ArgParser parser = new ArgParser();
        parser.registerArgument("input", "i", true, 1, "path of input file");
        parser.registerArgument("output", "o", false, 1, "path of output file");
        parser.registerArgument("ignore", "g", false, 1, "path of words-to-ignore file");

        ArgParser.ParsedArguments parsedArguments = parser.parse(args);
        String inputFilePath = getInputPath(parsedArguments);
        String outputFilePath = getOutputPath(parsedArguments);
        String ignoreWordsFilePath = getIgnoredWordsPath(parsedArguments);

        ADTController adtController = new ADTController();
        adtController.generateKWIC(inputFilePath, outputFilePath, ignoreWordsFilePath);
    }
}
