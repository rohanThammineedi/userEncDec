package com.mgen.pgen.encryption.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserApplicationResponse<T> {

    private T response;
    private List<ErrorDTO> errors;

    public UserApplicationResponse(List<ErrorDTO> errors) {
        this.errors = errors;
    }
}
