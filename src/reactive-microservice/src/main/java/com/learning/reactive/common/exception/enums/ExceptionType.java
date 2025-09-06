package com.learning.reactive.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionType {
  DATA_MISMATCH(HttpStatus.BAD_REQUEST, "Input parameters don't match each other."),
  INVALID_INPUT(HttpStatus.BAD_REQUEST, "Input parameter is not correct"),
  INVALID_FIELD_VALUE(
      HttpStatus.BAD_REQUEST, "Parameter passed for field is not meeting its required format."),
  PERMISSION_DENIED(HttpStatus.FORBIDDEN, "Forbidden");

  private final HttpStatus status;

  private final String description;
}
