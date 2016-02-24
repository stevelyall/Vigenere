import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by stevenlyall on 2016-02-23.
 */
public class Breaker {
    private final int LONGEST_KEY = 5;
    private ReadFile inFile;
    private WriteFile outFile;
    private String plaintextFilePath;

    public Breaker(String filePath, String outFilePath) throws FileNotFoundException {
        inFile = new ReadFile(filePath);
        plaintextFilePath = outFilePath;
    }

    public boolean breakCipher() throws IOException {
        removeOutputFile();
        outFile = new WriteFile(plaintextFilePath);

        String ciphertext = inFile.getFileContents();
        ciphertext = ciphertext.toUpperCase();
        String plaintext = "";

        // determine period of key
        int period = determineKeyPeriod(ciphertext);
        System.out.println("Key is of length " + period);

        // TODO find key
        return outFile.writeContents(plaintext);
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

        // calculate numerator
        for (int letter : letterCounts) {
            numerator += letter * (letter - 1);
        }
        // calculate denominator
        denominator = numLetters * (numLetters - 1);

        double IOC = (double) numerator / (double) denominator;
        return IOC;
    }

    private void removeOutputFile() {
        File file = new File(plaintextFilePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
