package unal.com.servidor.dto;


import lombok.Data;

@Data
public class ErrorDto {
  private String className;
  private int errorCode;
  private String detailedMessage;
  private String message;
  private String methodName;

}
