package com.mgen.pgen.encryption.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "Username is mandatory")
    private String username;
//    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Roles is mandatory")
    private String roles;
    @NotBlank(message = "Password is mandatory")
    private String encryptedPassword;
}