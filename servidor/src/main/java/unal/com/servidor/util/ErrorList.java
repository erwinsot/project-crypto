package unal.com.servidor.util;


import org.springframework.context.annotation.Configuration;
import unal.com.servidor.dto.ErrorDto;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class ErrorList {

  public List<ErrorDto> createErrorList(String className, String methodName, int errorCode) {
    List<ErrorDto> errorList = new ArrayList<>();
    ErrorDto errorDto = new ErrorDto();
    errorDto.setErrorCode(errorCode);
    errorDto.setClassName(className);
    errorDto.setMethodName(methodName);
    errorList.add(errorDto);
    return errorList;
  }
}
