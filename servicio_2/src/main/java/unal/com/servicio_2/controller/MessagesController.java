package unal.com.servicio_2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unal.com.servicio_2.Models.MessageReceive;
import unal.com.servicio_2.services.MessageService;

@RestController
public class MessagesController {

  @Autowired
  MessageService messageService;

  @PostMapping("/messages-receive")
  public ResponseEntity<?> postMessage(@RequestBody MessageReceive message) {
    System.out.println("mensaje recibido: " + message.getMessage());
    System.out.println("Password Recibido: " + message.getPassword());
    System.out.println("Key Recibida: " + message.getKey());

    try {
      ResponseEntity<?> messageSend = messageService.getMessage(message);
      return ResponseEntity.status(messageSend.getStatusCode().value()).body(messageSend.getBody());
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error al enviar el mensaje: " + e.getMessage());
    }
  }

}
