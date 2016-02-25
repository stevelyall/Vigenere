import java.io.IOException;
import java.util.Scanner;

public class Vigenere {

    public static void main(String[] args) {
        Vigenere vigenere = new Vigenere();


        System.out.println("Vigenere Cypher");
        String cmd = "";
        while (!cmd.equals("exit")) {
            printMenu();
            System.out.println("Enter command:");
            Scanner scanner = new Scanner(System.in);
            cmd = scanner.nextLine();
            if (!vigenere.execCmd(cmd, args)) {
                break;
            }
        }

        }

    private static void printMenu() {
        System.out.println("\nAvailable Commands:");
        System.out.println("encrypt\t- Encrypt a string");
        System.out.println("decrypt\t- Decrypt a string");
        System.out.println("break\t- Attempt to break the cipher used to encrypt a string");
        System.out.println("exit\t- Close the program");
    }

    private boolean execCmd(String cmd, String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (cmd.equals("encrypt")) {
            System.out.println("Enter text to encrypt:");
            String plaintext = scanner.nextLine();
            System.out.println("Enter key:");
            String key = scanner.nextLine();
            String ciphertext = encrypt(key, plaintext);
            System.out.println("Encrypted message:");
            System.out.println(ciphertext);
            return true;

        }
        if (cmd.equals("decrypt")) {
            System.out.println("Enter text to decrypt:");
            String ciphertext = scanner.nextLine();
            System.out.println("Enter key:");
            String key = scanner.nextLine();
            String plaintext = decrypt(key, ciphertext);
            System.out.println("Decrypted message:");
            System.out.println(plaintext);
            return true;
        }
        if (cmd.equals("break")) {
            String filePath = args[1];
            String outFilePath = args[2];
            breakCipher(filePath, outFilePath);
            return true;
        }
        if (cmd.equals("exit")) {
            System.out.println("Goodbye");
            return false;
        }

        System.out.println("Please enter a valid command.");
        return true;

    }

    private String breakCipher(String filePath, String outFilePath) {
        System.out.println("Breaking cipher in file " + filePath);
        try {
            BreakCipher b = new BreakCipher(filePath, outFilePath);
            b.breakCipher();
        } catch (IOException e) {
            System.err.println("An error occurred while attempting to break the cipher.");
            e.printStackTrace();
        }
        return "";
    }

    private String decrypt(String key, String ciphertext) {
        System.out.println("Decrypting text :" + ciphertext + "\nKey: " + key);
        Encryption e = new Encryption(key);
        return e.decrypt(ciphertext);
    }

    private String encrypt(String key, String plaintext) {
        System.out.println("Encrypting text :" + plaintext + "\nKey: " + key);
        Encryption e = new Encryption(key);
        return e.encrypt(plaintext);
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
