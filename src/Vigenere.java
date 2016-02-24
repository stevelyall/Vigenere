import java.io.IOException;

public class Vigenere {

    public static void main(String[] args) {
        Vigenere vigenere = new Vigenere();
        if (!vigenere.validateArguments(args)) {
            return;
        }

        System.out.println("Vigenere Cypher");

        if (args[0].equals("-e")) {
            String key = args[1];
            String inFilePath = args[2];
            String outFilePath = args[3];
            vigenere.encrypt(key, inFilePath, outFilePath);
        }
        if (args[0].equals("-d")) {
            String key = args[1];
            String filePath = args[2];
            String outFilePath = args[3];

            vigenere.decrypt(key, filePath, outFilePath);
        }
        if (args[0].equals("-b")) {
            String filePath = args[1];
            String outFilePath = args[3];
            vigenere.breakCipher(filePath, outFilePath);
        }

    }

    private void breakCipher(String filePath, String outFilePath) {
        System.out.println("Breaking cipher in file " + filePath);
        try {
            Breaker b = new Breaker(filePath, outFilePath);
            b.breakCipher();
        } catch (IOException e) {
            System.err.println("An error occurred while attempting to break the cipher.");
            e.printStackTrace();
        }
    }

    private void decrypt(String key, String inFilePath, String outFilePath) {
        System.out.println("Decrypting file " + inFilePath + " with key " + key);
        try {
            Decryption e = new Decryption(key, inFilePath, outFilePath);
            if (e.decrypt()) {
                System.out.println("File decrypted successfully!");
            } else {
                System.out.println("Decrypting failed.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while attempting to decrypt the file.");
            e.printStackTrace();
        }
    }

    private void encrypt(String key, String inFilePath, String outFilePath) {
        System.out.println("Encrypting file " + inFilePath + " with key " + key);
        try {
            Encryption e = new Encryption(key, inFilePath, outFilePath);
            if (e.encrypt()) {
                System.out.println("File encrypted successfully!");
            }
            else {
                System.out.println("Encrypting failed.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while attempting to encrypt the file.");
            e.printStackTrace();
        }
    }

    private boolean validateArguments(String[] args) {

        if (args.length < 2 || args.length > 4) {
            printHelpText();
            return false;
        }

        boolean validFlag = (args[0].equals("-e") || args[0].equals("-d" ) || args[0].equals("-b"));

        if (!validFlag)  {
            printHelpText();
            return false;
        }

        if ((args[0].equals("-e") || args[0].equals("-d")) && args.length != 4) {
            System.err.println("Error: Must supply key and file paths for decryption or encryption");
            System.err.println("Usage: Vigenere <mode> [<key>] <input file> <output file>");
            return false;
        }

        if (args[0].equals("-b") && args.length != 3) {
            System.err.println("Error: Must supply file names to break cipher");
            System.err.println("Usage: Vigenere <mode> <input file> <output file>");
            return false;
        }

        return true;
    }

    private void printHelpText() {
        System.err.println("Error: Must specify correct mode. -e for encrypt, -d to decrypt, -b to break");
        System.err.println("Usage: Vigenere <mode> [<key>] <input file> <output file>");
        System.err.println("\t-e\t<key> <input file> <output file>\t encrypt a file using the supplied key");
        System.err.println("\t-d\t<key> <input file> <output file>\t decrypt a file using the supplied key");
        System.err.println("\t-b\t<file>                          \t attempt to break the cipher");
    }
}
