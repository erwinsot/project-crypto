package unal.com.servidor.error;


import unal.com.servidor.dto.ErrorDto;

import java.util.List;

/**
 * Excepción personalizada para errores relacionados con la detención de rutas en Apache Camel.
 */


public class RouteStopException extends Exception {

    private final transient List<ErrorDto> errors;

    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message El mensaje de error.
     */
    public RouteStopException(String message, List<ErrorDto> errors) {
        super(message);
      this.errors = errors;
    }

    /**
     * Constructor que acepta un mensaje de error y una excepción de causa.
     *
     * @param message El mensaje de error.
     * @param cause   La causa de la excepción.
     */
    public RouteStopException(String message, Throwable cause, List<ErrorDto> errors) {
        super(message, cause);
      this.errors = errors;
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }
}

