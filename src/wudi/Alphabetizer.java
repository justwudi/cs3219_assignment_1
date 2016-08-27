package wudi;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Module that is in charge of sorting all the circularly shifted lines in ascending alphabetical
 * order.
 */
class Alphabetizer {

    private ArrayList<String> sortedLines;

    void alph(CircularShifter circularShifter) {
        sortedLines = circularShifter.getShiftedLines();
        Collections.sort(sortedLines);
    }

    Iterable<String> getAlphabeticallyShiftedLines() {
        return sortedLines;
    }
}
