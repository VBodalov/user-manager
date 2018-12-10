package com.vbodalov.usermanager.common.exceptionshandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ApiError {

    private HttpStatus status;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    private String debugMessage;

    private Collection<ApiSubError> subErrors;

    /**
     * Adds the validation errors to sub errors collection of ApiError object.
     *
     * @param constraintViolations the set of ConstraintViolation to add to sub errors collection
     */
    void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        Collection<ApiSubError> apiValidationErrors = constraintViolations.stream()
                .map(this::buildApiValidationError)
                .collect(toList());
        this.setSubErrors(apiValidationErrors);
    }

    /**
     * Utility method builds ApiValidationError from ConstraintViolation instance data.
     *
     * @param violation the ConstraintViolation to build ApiValidationError
     * @return detailed information about validation error
     */
    private ApiValidationError buildApiValidationError(ConstraintViolation<?> violation) {
        return ApiValidationError.builder()
                .objectName(violation.getRootBeanClass().getSimpleName())
                .fieldName(((PathImpl) violation.getPropertyPath()).getLeafNode().asString())
                .message(violation.getMessage())
                .build();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
