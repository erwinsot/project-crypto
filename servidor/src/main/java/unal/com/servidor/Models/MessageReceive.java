package unal.com.servidor.Models;

public class MessageReceive {

  private String password;
  private String nameService;
  private String message;
  private String version;
  private String destination;

  public MessageReceive() {
  }

  public MessageReceive(String password, String nameService, String message, String version,
                        String destination) {
    this.password = password;
    this.nameService = nameService;
    this.message = message;
    this.version = version;
    this.destination = destination;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNameService() {
    return nameService;
  }

  public void setNameService(String nameService) {
    this.nameService = nameService;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }
}
