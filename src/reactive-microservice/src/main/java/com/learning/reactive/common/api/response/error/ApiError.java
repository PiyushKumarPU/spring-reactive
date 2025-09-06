package com.learning.reactive.common.api.response.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@JsonCreator}))
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_EMPTY)
public class ApiError {

  @JsonProperty("code")
  @Schema(example = "VALIDATION_ERROR")
  private String code;

  @JsonProperty("message")
  @Schema(description = "Human readable error message", example = "validation error.")
  private String message;

  @JsonProperty("details")
  @Singular
  private List<ApiErrorDetail> details;

  @JsonProperty("trace")
  private String trace;
}
