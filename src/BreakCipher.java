import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stevenlyall on 2016-02-23.
 */
public class BreakCipher {
    private final int LONGEST_KEY = 5;
    private final double[] LETTER_FREQUENCIES =
            {0.08167, 0.01492, 0.02782, 0.04253, 0.12702,
                    0.02228, 0.02015, 0.06094, 0.06966, 0.00153, 0.00772,
                    0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.00095,
                    0.05987, 0.06327, 0.09056, 0.02758, 0.00978, 0.02360,
                    0.00150, 0.01974, 0.00074};
    private ReadFile inFile;
    private WriteFile outFile;
    private String plaintextFilePath;

    public BreakCipher(String filePath, String outFilePath) throws FileNotFoundException {
        inFile = new ReadFile(filePath);
        plaintextFilePath = outFilePath;
    }

    public boolean breakCipher() throws IOException {
        removeOutputFile();
        outFile = new WriteFile(plaintextFilePath);

        String ciphertext = inFile.getFileContents();
        ciphertext = ciphertext.toUpperCase();

        // determine period of key
        int period = determineKeyPeriod(ciphertext);
        System.out.println("Key is of length " + period);

        String plaintext = breakCipher(period, ciphertext);
        System.out.println("Plaintext:");
        System.out.println(plaintext);
        System.out.println("Done");
        return outFile.writeContents(plaintext);
    }

    private String breakCipher(int period, String ciphertext) {
        List<String> strings = new ArrayList<>(period);

        for (int i = 0; i < period; i++) {
            double minChiSquare = 9999.9;
            String bestCaesarShift = "";
            String str = "";

            for (int j = i; j < ciphertext.length(); j += period) {
                if (Character.isLetter(ciphertext.charAt(j))) {
                    str += ciphertext.charAt(j);
                }
            }

            // shift chars
            Map<Integer, String> shifts = new HashMap<>(26);
            for (int j = 0; j < 26; j++) {
                shifts.put(j, caesarShift(str, j));
            }

            for (int j = 0; j < shifts.size(); j++) {
                String shift = shifts.get(j);
                int letterCount[] = new int[26];
                for (int k = 0; k < shift.length(); k++) {
                    if (Character.isLetter(shift.charAt(k))) {
                        letterCount[shift.charAt(k) - 'A']++;
                    }
                }

                // determine chi square
                double shiftChiSquare = 0;
                for (int k = 0; k < shift.length(); k++) {
                    double expectedFrequency = shift.length() * LETTER_FREQUENCIES[k];
                    shiftChiSquare += (Math.pow((letterCount[k] - expectedFrequency), 2) / expectedFrequency);
                }

                // check for smallest chi square value
                if (shiftChiSquare < minChiSquare) {
                    minChiSquare = shiftChiSquare;
                    bestCaesarShift = shift;
                }
            }
            strings.add(i, bestCaesarShift);
        }

        //determine plaintext
        StringBuilder stringBuilder = new StringBuilder(ciphertext.length());
        for (int i = 0; i < strings.get(0).length(); i++) {
            for (int j = 0; j < period; j++) {
                if (strings.get(j).length() > i) {
                    stringBuilder.append(strings.get(j).charAt(i));
                }
            }
        }
        return stringBuilder.toString();
    }

    private String caesarShift(String toShift, int j) {
        String shifted = "";
        for (int k = 0; k < toShift.length(); k++) {
            char c = (char) (toShift.charAt(k) + j);
            if (c <= 'Z') {
                shifted += c;
            } else {
                shifted += (char) (c - 26);
            }
        }
        return shifted;
    }

    private int determineKeyPeriod(String ciphertext) {
        int period = 0;
        double indexOfCoincidence = 0.0;

        for (int i = 1; i <= LONGEST_KEY; i++) {

            // get sequences for key length
            int a = 0;
            String str = "";
            double stringIOC = 0.0;
            for (int j = 1; j < ciphertext.length(); j += i) {
                str += ciphertext.charAt(j);


                if (j + i >= ciphertext.length() && a != i) {
                    stringIOC += determineIOC(str);
                    j = a++ - i;
                } else if (j + i >= ciphertext.length()) {
                    stringIOC += determineIOC(str);
                }
            }

            stringIOC /= i;

            if (stringIOC > indexOfCoincidence) {
                indexOfCoincidence = stringIOC;
                period = i;
            }
        }
        return period;
    }

    private double determineIOC(String str) {
        int numLetters = 0, numerator = 0, denominator;

        int[] letterCounts = new int[26];
        for (int i = 0; i < letterCounts.length; i++) {
            letterCounts[i] = 0;
        }

        // count characters
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                letterCounts[str.charAt(i) - 'A']++;
                numLetters++;
            }
        }

        // numerator
        for (int letter : letterCounts) {
            numerator += letter * (letter - 1);
        }
        // denominator
        denominator = numLetters * (numLetters - 1);

        return (double) numerator / (double) denominator;
    }

    private void removeOutputFile() {
        File file = new File(plaintextFilePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
