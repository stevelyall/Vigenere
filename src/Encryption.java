/**
 * Created by stevenlyall on 2016-02-22.
 */
public class Encryption {

    private String key;

    public Encryption(String key) {
        this.key = key.toUpperCase();
    }

    public String encrypt(String plaintext) {
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
        return ciphertext;
    }

    public String decrypt(String ciphertext) {
        ciphertext = ciphertext.toUpperCase();
        String plaintext = "";

        // this portion adapated from http://rosettacode.org/wiki/Vigen%C3%A8re_cipher#Java
        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            plaintext += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return plaintext;
    }

}
