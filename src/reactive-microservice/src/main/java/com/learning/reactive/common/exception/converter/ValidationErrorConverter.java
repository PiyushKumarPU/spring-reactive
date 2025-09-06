package com.learning.reactive.common.exception.converter;

import com.learning.reactive.common.api.response.error.FieldValidationError;
import org.springframework.validation.BindingResult;

import java.util.List;

public class ValidationErrorConverter {

    public static List<FieldValidationError> convertBindingResultErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error -> new FieldValidationError(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()))
                .toList();
    }

}
