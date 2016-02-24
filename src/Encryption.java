import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by stevenlyall on 2016-02-22.
 */
public class Encryption {

    private ReadFile inFile;
    private WriteFile outFile;
    private String key;
    private String ciphertextFilePath;

    public Encryption(String key, String filePath, String outFilePath) throws FileNotFoundException {
        inFile = new ReadFile(filePath);
        ciphertextFilePath = outFilePath;
        this.key = key.toUpperCase();
    }

    public boolean encrypt() throws IOException {
        removeOutputFile();
        outFile = new WriteFile(ciphertextFilePath);

        String plaintext = inFile.getFileContents();
        plaintext = plaintext.toUpperCase();
        String ciphertext = "";

        // this portion adapated from http://rosettacode.org/wiki/Vigen%C3%A8re_cipher#Java
        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (c < 'A' || c > 'Z')  {
                continue;
            }
            ciphertext += (char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }
        return outFile.writeContents(ciphertext);
    }

    private void removeOutputFile() {
        File file = new File(ciphertextFilePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
