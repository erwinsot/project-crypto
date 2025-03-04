package unal.com.servidor.services;


import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import unal.com.servidor.Models.MessageReceive;
import unal.com.servidor.Models.MessageSend;
import unal.com.servidor.controller.Client;
import unal.com.servidor.entity.MessagesEntity;
import unal.com.servidor.repository.IDataRespository;
import unal.com.servidor.repository.MessageRepository;
import unal.com.servidor.util.GenerateCipher;

@Service
public class MessageService {

  @Autowired
  private MessageRepository messageRespository;
  @Autowired
  private GenerateCipher generateCipher;

  @Autowired
  private IDataRespository dataRespository;


  public ResponseEntity<String> postMessage(MessageReceive messageReceive) {

    try {
      // Generar clave y vector de inicialización (IV)
      SecretKey key = generateCipher.generateKey();
      IvParameterSpec iv = generateCipher.generateIv();

      // Cifrar los valores
      String encryptedMessage = generateCipher.encrypt(messageReceive.getMessage(), key, iv);
      String encryptedPassword = generateCipher.encrypt(messageReceive.getPassword(), key, iv);
      String destination = messageReceive.getDestination();
      String encryptedDestination = generateCipher.encrypt(messageReceive.getDestination(), key, iv);

      // Crear entidad para almacenar en la base de datos
      MessagesEntity messagesEntity = new MessagesEntity();
      messagesEntity.setMessage(encryptedMessage);
      messagesEntity.setPassword(encryptedPassword);
      messagesEntity.setDestination(encryptedDestination);
      messagesEntity.setKey(Base64.getEncoder().encodeToString(key.getEncoded())); // Guardar clave en Base64
      messagesEntity.setIv(Base64.getEncoder().encodeToString(iv.getIV())); // Guardar IV en Base64

      Map<String, String> serviceData = new HashMap<>();
      serviceData.put("message", encryptedMessage);
      serviceData.put("password", encryptedPassword);
      serviceData.put("destination", encryptedDestination);
      serviceData.put("key", Base64.getEncoder().encodeToString(key.getEncoded()));
      serviceData.put("iv", Base64.getEncoder().encodeToString(iv.getIV()));

      dataRespository.updateServiceData("Data", serviceData);
      // Guardar en la base de datos
      messageRespository.save(messagesEntity);



      sendMessageToService(messageReceive.getDestination(), encryptedMessage, encryptedPassword,messageReceive.getVersion() , key, iv);

      return ResponseEntity.ok("Message saved successfully");
    } catch (GeneralSecurityException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Encryption error: " + e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error saving message: " + e.getMessage());
    }
  }

  public void sendMessageToService(String destination, String encryptedMessage,
                                   String encryptedPassword, String version,
                                   SecretKey key, IvParameterSpec iv) {

    String url = "http://host.docker.internal:"+ destination;
    System.out.println("Enviando mensaje a " + url);
    Client client = new Client(url);
    MessageSend messageSend = new MessageSend();
    messageSend.setMessage(encryptedMessage);
    messageSend.setPassword(encryptedPassword);
    messageSend.setVersion(version);
    messageSend.setKey(Base64.getEncoder().encodeToString(key.getEncoded()));
    messageSend.setIv(Base64.getEncoder().encodeToString(iv.getIV()));

    client.sendPostRequest(messageSend);
  }
}
