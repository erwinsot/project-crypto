package unal.com.servidor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unal.com.servidor.Models.MessageReceive;
import unal.com.servidor.services.MessageService;

@RestController
public class MessagesController {

  @Autowired
  MessageService messageService;

  @PostMapping("/messages")
  public ResponseEntity<?> postMessage(@RequestBody MessageReceive message) {
    try {
      ResponseEntity<?> messageSend = messageService.postMessage(message);
      return ResponseEntity.status(messageSend.getStatusCode().value()).body(messageSend.getBody());
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error al enviar el mensaje: " + e.getMessage());
    }
  }

}
