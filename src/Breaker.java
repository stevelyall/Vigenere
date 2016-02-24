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
        // todo determine IOC

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
            for (int j = 0; j < ciphertext.length(); j += i) {
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
        int numLetters = 0, totalChars = 0, numerator = 0, denominator = 0;
        int[] letters = new int[26];

        for (int i = 0; i < letters.length; i++) {
            letters[i] = 0;
        }

        // calculate numerator
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                letters[str.charAt(i) - 'A']++;
                totalChars++;
            }
        }

        // calculate denominator
        denominator = totalChars * (numLetters - 1);

        return (double) numerator / (double) denominator;


    }


    private void removeOutputFile() {
        File file = new File(plaintextFilePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
