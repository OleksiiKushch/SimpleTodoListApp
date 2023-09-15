package com.example.simpletodolistapp.validator;

import com.example.simpletodolistapp.dto.RegistrationData;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

import static com.example.simpletodolistapp.constants.SimpleTodoListConstants.*;

@Component
public class RegistrationDataValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return RegistrationData.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        RegistrationData registrationData = (RegistrationData) target;
        if (Objects.isNull(registrationData.getUsername()) || registrationData.getUsername().isBlank()) {
            errors.rejectValue(USERNAME_FIELD, USERNAME_EMPTY_ERROR_CODE);
        } else if (Objects.isNull(registrationData.getPassword()) || registrationData.getPassword().isBlank()) {
            errors.rejectValue(PASSWORD_FIELD, PASSWORD_EMPTY_ERROR_CODE);
        } else if (!registrationData.getPassword().equals(registrationData.getConfirmationPassword())) {
            errors.rejectValue(CONFIRMATION_PASSWORD_FIELD, CONFIRMATION_PASSWORD_DOES_NOT_MATCH_ERROR_CODE);
        }
    }
}
