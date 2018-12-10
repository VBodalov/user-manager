package com.vbodalov.usermanager.common.exceptionshandling;

import com.google.gson.Gson;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
class ApiValidationError implements ApiSubError{

    private String objectName;
    private String fieldName;
    private Object rejectedValue;
    private String message;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
