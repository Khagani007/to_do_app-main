package com.khagani.to_do_app.annotation;

import com.khagani.to_do_app.dto.RegistrationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, RegistrationDTO> {

    @Override
    public boolean isValid(RegistrationDTO value, ConstraintValidatorContext context) {
        return value == null || Objects.equals(value.getPassword(), value.getConfirmPassword());
    }


}
