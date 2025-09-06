package com.learning.reactive.common.api.response.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.learning.reactive.common.exception.BusinessException;
import com.learning.reactive.common.exception.enums.ExceptionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor(onConstructor = @__({@JsonCreator}))
public class FieldValidationError {

  @JsonProperty("fieldName")
  @Schema(example = "firstName")
  private String fieldName;

  @JsonProperty("rejectedValue")
  @Schema(example = "+homas")
  private Object rejectedValue;

  @JsonProperty("message")
  @Schema(
      example = "Invalid character in firstName field.",
      description = "Human readable error message.")
  private String message;

  public static void throwExceptionIfInvalidField(
      Stream<Collection<FieldValidationError>> errorStream) {
    List<FieldValidationError> errors = errorStream.flatMap(Collection::stream).toList();
    if (!errors.isEmpty()) {
        throw BusinessException.builder(ExceptionType.INVALID_INPUT)
                .validationErrors(errors)
                .build();
    }
  }
}
