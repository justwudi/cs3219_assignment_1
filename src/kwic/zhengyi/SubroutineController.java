package kwic.zhengyi;

import kwic.IOProcessingException;
import kwic.KWICGenerator;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Master Control for the KWIC (Key Word In Context) index system.
 *
 * This is implemented with the Main Program/Subroutine with Shared Data architecture.
 */
public class SubroutineController implements KWICGenerator {

    private final String SINGLE_WHITESPACE = " ";

    private Scanner reader = new Scanner(System.in);
    private ArrayList<String> lines = new ArrayList<>();
    private ArrayList<String> circularShiftedLines = new ArrayList<>();
    private StringBuilder stringBuilder = new StringBuilder();
    private HashSet<String> ignoredWords = new HashSet<>();
    private File outputFile;

    private void readAndStore(String path, Collection<String> store, String fileDescription) throws IOProcessingException {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                store.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new IOProcessingException(fileDescription + " not Found!");
        } catch (IOException e) {
            throw new IOProcessingException("Error reading " + fileDescription + "!");
        }
    }

    private void readFromInputFile(String inputFilePath) throws IOProcessingException {
        if (inputFilePath != null) {
            readAndStore(inputFilePath, lines, "Input File");
        }
    }

    private void getIgnoreWords(String ignoreWordsFilePath) throws IOProcessingException {
        if (ignoreWordsFilePath != null) {
            readAndStore(ignoreWordsFilePath, ignoredWords, "Words-to-ignore File");
        }
    }

    private void exitProgram() {
        System.exit(1);
    }

    private void checkOutputPath(String outputFilePath) throws IOProcessingException {
        outputFile = new File(outputFilePath);

        if (outputFile.isDirectory()) {
            throw new IOProcessingException("Output file cannot be a directory!");
        } else if (outputFile.exists()) {
            System.out.print("Output file exists. Enter [y] to overwrite, or any other key to exit: ");
            String input = reader.next();

            if (!input.trim().equalsIgnoreCase("y")) {
                exitProgram();
            }
        }
    }

    private String[] splitStringToWords(String str) {
        return str.split("\\s+");
    }

    private void circularShiftLines() {
        for (String line : lines) {
            String[] words = splitStringToWords(line);
            circularShiftedLines.addAll(shiftLines(words));
        }
    }

    private ArrayList<String> shiftLines(String[] words) {
        ArrayList<String> shiftedLines = new ArrayList<>();

        IntStream.range(0, words.length).forEach(index -> {
            String currentWord = words[index];
            if (!ignoredWords.contains(currentWord.toLowerCase())) {
                String shiftedLine = createShiftedLine(words, index);
                shiftedLines.add(shiftedLine);
            }
        });

        return shiftedLines;
    }

    private String createShiftedLine(String[] words, int startIndex) {
        clearStringBuilder();

        IntStream.range(startIndex, words.length).forEachOrdered(index -> {
            stringBuilder.append(words[index]);
            stringBuilder.append(SINGLE_WHITESPACE);
        });

        IntStream.range(0, startIndex).forEachOrdered(index -> {
            stringBuilder.append(words[index]);
            stringBuilder.append(SINGLE_WHITESPACE);
        });

        // Remove the trailing whitespace
        removeLastChar();

        return stringBuilder.toString();
    }

    private void clearStringBuilder() {
        stringBuilder.setLength(0);
        stringBuilder.trimToSize();
    }

    private void removeLastChar() {
        stringBuilder.setLength(stringBuilder.length() - 1);
    }

    private void alphabetize() {
        Collections.sort(circularShiftedLines);
    }

    private void generateOutputFile() throws IOProcessingException {
        try {
            FileWriter fileWriter = new FileWriter(outputFile, false);

            for (String line : circularShiftedLines) {
                fileWriter.write(line);
                fileWriter.write("\n");
            }

            fileWriter.close();
        } catch (IOException e) {
            throw new IOProcessingException("Error writing to output file!");
        }
    }

    private void initializeGenerator() {
        lines.clear();
        circularShiftedLines.clear();
        ignoredWords.clear();
        outputFile = null;
        clearStringBuilder();
    }

    public void generateKWIC(String inputFilePath, String outputFilePath, String ignoreWordsFilePath) {
        initializeGenerator();

        try {
            readFromInputFile(inputFilePath);
            getIgnoreWords(ignoreWordsFilePath);
            checkOutputPath(outputFilePath);
            circularShiftLines();
            alphabetize();
            generateOutputFile();
        } catch (IOProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
