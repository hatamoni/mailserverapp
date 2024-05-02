package de.hixdev.mailserverapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailUpdateNotPossibleException extends RuntimeException {

  public EmailUpdateNotPossibleException(String resourceName, Long emailId,
      String fieldName, String fieldValue) {
    super(String.format(
        "Emails can only be Updated in State 'DRAFT'. Resource %s with id has %s : '%s'",
        resourceName, emailId, fieldName, fieldValue));
  }
}
