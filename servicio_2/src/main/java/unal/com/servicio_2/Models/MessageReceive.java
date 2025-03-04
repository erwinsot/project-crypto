package unal.com.servicio_2.Models;

public class MessageReceive {

  private String password;
  private String nameService;
  private String message;
  private String version;
  private String key;
  private String iv;

  public MessageReceive() {
  }

  public MessageReceive(String password, String nameService, String message, String version,
                        String key, String iv) {
    this.password = password;
    this.nameService = nameService;
    this.message = message;
    this.version = version;
    this.key = key;
    this.iv = iv;
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

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getIv() {
    return iv;
  }

  public void setIv(String iv) {
    this.iv = iv;
  }
}
