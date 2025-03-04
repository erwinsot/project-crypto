package unal.com.servidor.util;


import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import org.springframework.stereotype.Service;

@Service
public class GenerateCipher {

  public SecretKey generateKey() throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(256); // Tamaño de la clave (128, 192 o 256 bits)
    return keyGenerator.generateKey();
  }

  public IvParameterSpec generateIv() {
    byte[] iv = new byte[16]; // Tamaño del IV para AES/CBC
    new SecureRandom().nextBytes(iv);
    return new IvParameterSpec(iv);
  }

  public static String encrypt( String input, SecretKey key, IvParameterSpec iv) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
    byte[] cipherText = cipher.doFinal(input.getBytes());
    return Base64.getEncoder().encodeToString(cipherText);
  }

  public static String decrypt(String cipherText, SecretKey key, IvParameterSpec iv) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, key, iv);
    byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
    return new String(plainText);
  }
}
