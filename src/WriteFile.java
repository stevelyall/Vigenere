import java.io.*;

/**
 * Created by stevenlyall on 2016-02-22.
 */
public class WriteFile extends BufferedWriter {

    public WriteFile(String file) throws IOException {
        super(new FileWriter(new File(file)));
    }

    public boolean writeContents(String contents) {
        try {
            this.write(contents, 0, contents.length());
            this.flush();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
