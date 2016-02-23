public class Vigenere {

    public static void main(String[] args) {
        if (!validateArguments(args)) {
            return;
        }
    System.out.println("Vigenere Cypher");
    }

    private static boolean validateArguments(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.err.println("Error: Incorrect arguments supplied.");
            System.err.println("Usage: Vigenere <mode> [key] <file>");
            System.err.println("\t-e\t<key> <file>\t encrypt a file using the supplied key");
            System.err.println("\t-d\t<key> <file>\t decrypt a file using the supplied key");
            System.err.println("\t-b\t<file>      \t attempt to break the cipher");
            return false;
        }
        if (args[0] != "-e" || args[0]!= "-d" || args[0] != "-b") {
            System.err.println("Error: Must specify correct mode. -e for encrypt, -d to decrypt, -b to break");
            System.err.println("Usage: Vigenere <mode> [key] <file>");
            System.err.println("\t-e\t encrypt the file");
            System.err.println("\t-d\t decrypt the file");
            return false;
        }
        if (args[1] == null) {
            System.err.println("Error: Must supply key for decryption and encryption");
            System.err.println("Usage: Vigenere <mode> [key] <file>");
            return false;
        }
        return true;
    }
}
