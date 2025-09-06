package com.learning.reactive.common.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.learning.reactive.common.api.response.error.ApiError;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_EMPTY)
public class APIResponse<T> {

    @JsonProperty("success")
    @Schema(description = "true|false indicating if request completed successfully.")
    private Boolean success;

    @JsonProperty("payload")
    private T payload;

    @JsonProperty("error")
    @Schema(example = "Request submitted successfully.")
    private ApiError error;

    @JsonProperty("message")
    @Schema(example = "Request submitted successfully.")
    private String message;

    public static <T> APIResponse<T> success(T payload) {
        return APIResponse.<T>builder()
                .success(true)
                .payload(payload)
                .build();
    }

    public static <T> APIResponse<T> success(String message) {
        return APIResponse.<T>builder()
                .success(true)
                .message(message)
                .build();
    }

    public static <T> APIResponse<T> success(T payload, String message) {
        return APIResponse.<T>builder()
                .success(true)
                .payload(payload)
                .message(message)
                .build();
    }

    public static <T> APIResponse<T> failure(String code, String message) {
        return APIResponse.<T>builder()
                .success(false)
                .error(ApiError.builder().code(code).message(message).build())
                .build();
    }

    public static <T> APIResponse<T> failure(String message) {
        return APIResponse.<T>builder()
                .success(false)
                .build();
    }

    public static <T> APIResponse<T> failure(ApiError error) {
        return APIResponse.<T>builder()
                .success(false)
                .error(error)
                .build();
    }

    public static <T> APIResponse<T> failure(T payload, String message) {
        return APIResponse.<T>builder()
                .success(false)
                .payload(payload)
                .message(message)
                .build();
    }
}
