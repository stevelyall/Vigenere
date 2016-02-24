import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by stevenlyall on 2016-02-22.
 */
public class Decryption {

    private ReadFile inFile;
    private WriteFile outFile;
    private String key;
    private String plaintextFilePath;

    public Decryption(String key, String filePath, String outFilePath) throws FileNotFoundException {
        inFile = new ReadFile(filePath);
        plaintextFilePath = outFilePath;
        this.key = key.toUpperCase();
    }
    public boolean decrypt() throws IOException {
        removeOutputFile();
        outFile = new WriteFile(plaintextFilePath);

        String ciphertext = inFile.getFileContents();
        ciphertext = ciphertext.toUpperCase();
        String plaintext = "";

        // this portion adapated from http://rosettacode.org/wiki/Vigen%C3%A8re_cipher#Java
        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            plaintext += (char)((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return outFile.writeContents(plaintext);
    }

    private void removeOutputFile() {
        File file = new File(plaintextFilePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
