import java.io.*;

/**
 * Created by stevenlyall on 2016-02-22.
 */
public class ReadFile extends BufferedReader {

    public ReadFile(String path) throws FileNotFoundException {
        super(new FileReader(new File(path)));
    }

    public String getFileContents() {
        String contents = "";
        String line = "";

        try {
            while ((line = this.readLine()) != null) {
                contents += line+'\n';
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file");
            e.printStackTrace();
        }
        return contents;
    }
}
