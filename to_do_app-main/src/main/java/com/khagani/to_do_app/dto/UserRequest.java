package com.khagani.to_do_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank
    private String name;
    @NotNull
    @Email(regexp = "^\\w+@\\w+\\.\\w+$")
    private String email;
    private String password;
}
