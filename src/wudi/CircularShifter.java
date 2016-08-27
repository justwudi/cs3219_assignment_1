package wudi;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * The module in charge of creating a set of circularly shifted lines, where the first word does
 * not include words within the "words-to-ignore" set.
 */
class CircularShifter {

    private String SINGLE_WHITESPACE = " ";

    private ArrayList<String> shiftedLines;
    private StringBuilder stringBuilder = new StringBuilder();

    void setup(LineStorage lineStorage, Set<String> ignoredWords) {
        shiftedLines = new ArrayList<>();

        for (String line : lineStorage) {
            String[] words = splitStringToWords(line);
            shiftedLines.addAll(shiftLines(words, ignoredWords));
        }
    }

    ArrayList<String> getShiftedLines() {
        return shiftedLines;
    }

    private String[] splitStringToWords(String str) {
        return str.split("\\s+");
    }

    private ArrayList<String> shiftLines(String[] words, Set<String> ignoredWords) {
        ArrayList<String> lines = new ArrayList<>();

        IntStream.range(0, words.length).forEach(index -> {
            String currentWord = words[index];
            if (!ignoredWords.contains(currentWord.toLowerCase())) {
                String shiftedLine = createShiftedLine(words, index);
                lines.add(shiftedLine);
            }
        });

        return lines;
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
}
