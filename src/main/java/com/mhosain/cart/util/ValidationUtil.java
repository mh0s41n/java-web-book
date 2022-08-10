package com.mhosain.cart.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Map;
import java.util.stream.Collectors;

public class ValidationUtil {
    public static final ValidationUtil INSTANCE = new ValidationUtil();

    private final Validator validator;

    private ValidationUtil() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static ValidationUtil getInstance() {
        return INSTANCE;
    }

    public <T> Map<String, String> validate(T object) {
        var violations = validator.validate(object);

        return violations.stream().collect(
                Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (errMsg1, errMsg2) -> errMsg1 + "<br>" + errMsg2
                )
        );
    }
}
