package com.khagani.to_do_app.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

    @Autowired
    private ApplicationContext context;
    private Object repository;

    private String methodName;

    public UniqueValidator() {
    }

    @Override
    public void initialize(Unique constrain) {
        this.repository = this.context.getBean(constrain.repositoryClass());
        this.methodName = constrain.methodName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return !(Boolean) this.repository.getClass().getMethod(this.methodName, String.class).invoke(this.repository, value);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException var4) {
            throw new RuntimeException(var4);
        }
    }
}
