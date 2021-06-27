package com.woulter.document.error;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.woulter.document.dto.ErrorAttributes;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final ErrorConstants errorConstants;

  /**
   * Handle file format not supported exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler({FileFormatNotSupportedException.class})
  public ResponseEntity<ErrorAttributes> handleFileFormatNotSupportedException(FileFormatNotSupportedException ex) {
    ErrorAttributes errorAttributes = errorConstants.getFileFormatNotSupportedException();
    errorAttributes.setStatus(HttpStatus.BAD_REQUEST.value());
    errorAttributes.setParams(List.of(ex.getMessage()));
    log.error("{} :: {}", errorAttributes.getCode(), errorAttributes.getMessage());
    return new ResponseEntity<>(errorAttributes, HttpStatus.BAD_REQUEST);
  }
}
