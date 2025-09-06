package com.learning.reactive.common.exception;

import com.learning.reactive.common.api.response.APIResponse;
import com.learning.reactive.common.api.response.error.ApiErrorDetail;
import com.learning.reactive.common.api.response.error.FieldValidationError;
import com.learning.reactive.common.exception.enums.ErrorCode;
import com.learning.reactive.common.exception.enums.ExceptionType;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
public class BusinessException extends RuntimeException {

    private final ExceptionType exceptionType;
    private final List<FieldValidationError> validationErrors;
    private final List<ApiErrorDetail> errorDetail;
    private final APIResponse<?> apiResponse;
    private final String table;
    private final String internalNotes;

    private BusinessException(Builder builder) {
        super(builder.message != null ? builder.message : builder.exceptionType.toString(), builder.cause);
        this.exceptionType = builder.exceptionType;
        this.validationErrors = builder.validationErrors;
        this.errorDetail = builder.errorDetail;
        this.apiResponse = builder.apiResponse;
        this.table = builder.table;
        this.internalNotes = builder.internalNotes;
    }

    public static Builder builder(ExceptionType exceptionType) {
        return new Builder(exceptionType);
    }

    public static class Builder {
        private final ExceptionType exceptionType;
        private String message;
        private Throwable cause;
        private List<FieldValidationError> validationErrors;
        private List<ApiErrorDetail> errorDetail;
        private APIResponse<?> apiResponse;
        private String table;
        private String internalNotes;

        public Builder(ExceptionType exceptionType) {
            this.exceptionType = exceptionType;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder cause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public Builder validationErrors(List<FieldValidationError> validationErrors) {
            this.validationErrors = validationErrors;
            return this;
        }

        public Builder errorDetail(List<ApiErrorDetail> errorDetail) {
            this.errorDetail = errorDetail;
            return this;
        }

        public Builder errorCode(ErrorCode errorCode) {
            this.errorDetail = new ArrayList<>();
            this.errorDetail.add(new ApiErrorDetail(errorCode.name(), null, null, null));
            return this;
        }

        public Builder apiResponse(APIResponse<?> apiResponse) {
            this.apiResponse = apiResponse;
            return this;
        }

        public Builder table(String table) {
            this.table = table;
            return this;
        }

        public Builder internalNotes(String internalNotes) {
            this.internalNotes = internalNotes;
            return this;
        }

        public BusinessException build() {
            return new BusinessException(this);
        }
    }
}
