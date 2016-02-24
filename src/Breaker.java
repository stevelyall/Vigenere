import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by stevenlyall on 2016-02-23.
 */
public class Breaker {
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

        // TODO

        return outFile.writeContents(plaintext);
    }

    private void removeOutputFile() {
        File file = new File(plaintextFilePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
