package unal.com.servicio_2.services;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import unal.com.servicio_2.Models.MessageReceive;
import unal.com.servicio_2.util.GenerateCipher;

@Service
public class MessageService {
  @Autowired
  private GenerateCipher generateCipher;

  public ResponseEntity<String> getMessage(MessageReceive messageReceive) throws Exception {
    String message =messageReceive.getMessage();
    String key = messageReceive.getKey();
    String iv = messageReceive.getIv();
    String password= messageReceive.getPassword();

    SecretKey secretKey = generateCipher.convertBase64ToSecretKey(key);
    IvParameterSpec ivParameterSpec = generateCipher.convertBase64ToIv(iv);
    String decryptedMessage = generateCipher.decrypt(message, secretKey, ivParameterSpec);
    String decryptedPassword = generateCipher.decrypt(password, secretKey, ivParameterSpec);

    System.out.println("**** Mensaje despues de desencriptar: "+ decryptedMessage );
    System.out.println("**** Passsword despues de decencriptar: " + decryptedPassword);
    return ResponseEntity.ok("Mensaje: " + decryptedMessage + " Contraseña: " + decryptedPassword);

  }


  


}
