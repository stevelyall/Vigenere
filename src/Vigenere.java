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
            System.out.println("Enter ciphertext to analyze:");
            String ciphertext = scanner.nextLine();
            String plaintext = breakCipher(ciphertext);
            System.out.println("The message is:");
            System.out.println(plaintext);
            return true;
        }
        if (cmd.equals("exit")) {
            System.out.println("Goodbye");
            return false;
        }

        System.out.println("Please enter a valid command.");
        return true;

    }

    private String breakCipher(String ciphertext) {
        System.out.println("Breaking cipher in message: " + ciphertext);
        BreakCipher b = new BreakCipher();
        return b.breakCipher(ciphertext);
    }

    private String decrypt(String key, String ciphertext) {
        System.out.println("Decrypting text: " + ciphertext + "\nKey: " + key);
        Encryption e = new Encryption(key);
        return e.decrypt(ciphertext);
    }

    private String encrypt(String key, String plaintext) {
        System.out.println("Encrypting text: " + plaintext + "\nKey: " + key);
        Encryption e = new Encryption(key);
        return e.encrypt(plaintext);
    }

}
