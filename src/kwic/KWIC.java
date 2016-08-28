package kwic;

import kwic.wudi.ADTController;
import kwic.zhengyi.SubroutineController;

/**
 * Key Word In Context
 */
public class KWIC {

    private static KWICArchitecture getArchitecture(ArgParser.ParsedArguments parsedArguments) {
        KWICArchitecture architecture = KWICArchitecture.ADT;

        if (parsedArguments.hasArgument("architecture")) {
            String argument = parsedArguments.getArgument("architecture");

            if (argument.equalsIgnoreCase("subroutine")) {
                architecture = KWICArchitecture.SUBROUTINE;
            }
        }

        return architecture;
    }

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
        parser.registerArgument("architecture", "a", false, 1, "type of architecture, i.e. adt, subroutine");
        parser.registerArgument("input", "i", true, 1, "path of input file");
        parser.registerArgument("output", "o", false, 1, "path of output file");
        parser.registerArgument("ignore", "g", false, 1, "path of words-to-ignore file");

        ArgParser.ParsedArguments parsedArguments = parser.parse(args);
        String inputFilePath = getInputPath(parsedArguments);
        String outputFilePath = getOutputPath(parsedArguments);
        String ignoreWordsFilePath = getIgnoredWordsPath(parsedArguments);
        KWICArchitecture architecture = getArchitecture(parsedArguments);
        KWICGenerator generator;

        switch (architecture) {
            case ADT:
                generator = new ADTController();
                break;
            case SUBROUTINE:
                generator = new SubroutineController();
                break;
            default:
                generator = new ADTController();
        }

        generator.generateKWIC(inputFilePath, outputFilePath, ignoreWordsFilePath);
    }
}
