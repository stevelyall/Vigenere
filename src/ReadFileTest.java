import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * Created by stevenlyall on 2016-02-22.
 */
public class ReadFileTest {

    ReadFile rf;
    final String PATH = "testFile";
    @org.junit.Before
    public void setUp() throws Exception {
        System.out.println(System.getProperty("user.dir"));

        rf = new ReadFile(PATH);
    }

    @org.junit.Test
    public void testGetFileContents() throws Exception {
        String result = rf.getFileContents();
        System.out.println(result);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(PATH))));
        int charCount = 0;
        while(reader.read() > -1) {
            charCount++;
        }
        reader.close();

        assertEquals(charCount, result.length());
    }
}