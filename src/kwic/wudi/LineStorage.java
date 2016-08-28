package kwic.wudi;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Module that is in charge of holding the lines to be circularly shifted.
 */
class LineStorage implements Iterable<String> {

    private ArrayList<String> lines;

    LineStorage() {
        lines = new ArrayList<>();
    }

    void addLine(String line) {
        lines.add(line);
    }

    @Override
    public Iterator<String> iterator() {
        return lines.iterator();
    }
}
