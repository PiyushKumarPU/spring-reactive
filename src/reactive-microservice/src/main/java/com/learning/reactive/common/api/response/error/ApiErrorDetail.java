package com.learning.reactive.common.api.response.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@JsonCreator}))
@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_EMPTY)
public class ApiErrorDetail {

  @JsonProperty("code")
  @Schema(example = "VALIDATION_ERROR")
  private String code;

  @JsonProperty("field")
  @Schema(example = "id")
  private String field;

  @JsonProperty("message")
  @Schema(description = "Human readable error message", example = "validation error.")
  private String message;

  @JsonProperty("rejectedValue")
  @Schema(example = "i-am-not-a-uuid")
  private Object rejectedValue;
}
