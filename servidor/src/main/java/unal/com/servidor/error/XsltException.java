package unal.com.servidor.error;

import lombok.Data;

@Data
public class XsltException extends IllegalArgumentException {

  private final String detailedMessage;
  private final int errorCode;

  // Constructor principal con mensaje, mensaje detallado y código de error
  public XsltException(String message, String detailedMessage, int errorCode) {
    super(message); // Utiliza el campo message de la clase padre
    this.detailedMessage = detailedMessage;
    this.errorCode = errorCode;
  }

  // Constructor con mensaje y causa
  public XsltException(String message, Throwable cause, String detailedMessage, int errorCode) {
    super(message, cause);
    this.detailedMessage = detailedMessage;
    this.errorCode = errorCode;
  }

  // Constructor con solo la causa
  public XsltException(Throwable cause, String detailedMessage, int errorCode) {
    super(cause);
    this.detailedMessage = detailedMessage;
    this.errorCode = errorCode;
  }

  // Constructor vacío
  public XsltException(String detailedMessage, int errorCode) {
    super();
    this.detailedMessage = detailedMessage;
    this.errorCode = errorCode;
  }
}
