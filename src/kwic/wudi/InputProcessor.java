package kwic.wudi;

import kwic.IOProcessingException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Module that is in charge of processing input files.
 */
class InputProcessor {

    LineStorage processFile(String inputFilePath) throws IOProcessingException {
        LineStorage lineStorage = new LineStorage();

        try {
            FileReader fileReader = new FileReader(inputFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lineStorage.addLine(line);
            }
        } catch (FileNotFoundException e) {
            throw new IOProcessingException("Input file not Found!");
        } catch (IOException e) {
            throw new IOProcessingException("Error reading input file!");
        }

        return lineStorage;
    }
}
