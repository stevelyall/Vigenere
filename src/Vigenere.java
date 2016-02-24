public class Vigenere {

    public static void main(String[] args) {
        Vigenere vigenere = new Vigenere();
        if (!vigenere.validateArguments(args)) {
            return;
        }

        System.out.println("Vigenere Cypher");

        if (args[0].equals("-e")) {
            String key = args[1];
            String filePath = args[2];
            vigenere.encrypt(key, filePath);
        }
        if (args[0].equals("-d")) {
            String key = args[1];
            String filePath = args[2];
            vigenere.decrypt(key, filePath);
        }
        if (args[0].equals("-b")) {
            String filePath = args[1];
            vigenere.breakCipher(filePath);
        }

    }

    private void breakCipher(String filePath) {
        System.out.println("Breaking cipher in file " + filePath);
    }

    private void decrypt(String key, String filePath) {
        System.out.println("Decrypting file " + filePath + " with key " + key);
    }

    private void encrypt(String key, String filePath) {
        System.out.println("Encrypting file " + filePath + " with key " + key);
    }

    private boolean validateArguments(String[] args) {

        if (args.length < 2 || args.length > 3) {
            printHelpText();
            return false;
        }

        boolean validFlag = (args[0].equals("-e") || args[0].equals("-d" ) || args[0].equals("-b"));

        if (!validFlag)  {
            printHelpText();
            return false;
        }

        if ((args[0].equals("-e") || args[0].equals("-d")) && args.length != 3) {
            System.err.println("Error: Must supply key and file for decryption or encryption");
            System.err.println("Usage: Vigenere <mode> [<key>] <file>");
            return false;
        }

        if (args[0].equals("-b") && args.length != 2) {
            System.err.println("Error: Must supply file name to break cipher");
            System.err.println("Usage: Vigenere <mode> <file>");
            return false;
        }

        return true;
    }

    private void printHelpText() {
        System.err.println("Error: Must specify correct mode. -e for encrypt, -d to decrypt, -b to break");
        System.err.println("Usage: Vigenere <mode> [<key>] <file>");
        System.err.println("\t-e\t<key> <file>\t encrypt a file using the supplied key");
        System.err.println("\t-d\t<key> <file>\t decrypt a file using the supplied key");
        System.err.println("\t-b\t<file>      \t attempt to break the cipher");
    }
}
