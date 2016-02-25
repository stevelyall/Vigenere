import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by stevenlyall on 2016-02-22.
 */
public class WriteFileTest {
    final String PATH_READ = "testFile";
    final String PATH_WRITE = "testFile-WriteTest";
    WriteFile wf;
    String toWrite;

    @Before
    public void setUp() throws Exception {
        File file = new File(PATH_WRITE);
        if (file.exists()) {
            file.delete();
        }

        ReadFile rf = new ReadFile(PATH_READ);
        toWrite = rf.getFileContents();

        wf = new WriteFile(PATH_WRITE);
    }

    @Test
    public void testWriteContents() throws Exception {
        boolean written = wf.writeContents(toWrite);
        assertTrue(written);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(PATH_WRITE))));
        int writeCount = 0;
        while(reader.read() > -1) {
            writeCount++;
        }
        reader.close();

        assertEquals(toWrite.length(), writeCount);
    }
}