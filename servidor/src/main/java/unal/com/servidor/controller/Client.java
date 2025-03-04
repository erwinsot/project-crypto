package unal.com.servidor.controller;


import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import unal.com.servidor.Models.MessageSend;



public class Client {
  private final WebClient webClient;

  public Client(String baseUrl) {
    this.webClient = WebClient.create(baseUrl);
  }

  public String sendPostRequest(MessageSend messageSend) {
    return webClient.post()
        .uri("/messages-receive")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(messageSend)
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}
