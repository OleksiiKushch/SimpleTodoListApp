package com.example.simpletodolistapp.validator;

import com.example.simpletodolistapp.dto.RegistrationData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import static com.example.simpletodolistapp.constants.SimpleTodoListConstants.*;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationDataValidatorTest {

    private static final String VALID_USERNAME = "testUser";
    private static final String VALID_PASSWORD = "password";
    private static final String EMPTY_LINE = "";
    private static final String MISMATCHED_CONFIRMATION_PASSWORD = "mismatchedPassword";

    @InjectMocks
    private RegistrationDataValidator validator;

    @Captor
    private ArgumentCaptor<String> fieldArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> errorCodeArgumentCaptor;

    @Mock
    private RegistrationData registrationData;
    @Mock
    private Errors errors;

    @Before
    public void setUp() {
        when(registrationData.getUsername()).thenReturn(VALID_USERNAME);
        when(registrationData.getPassword()).thenReturn(VALID_PASSWORD);
        when(registrationData.getConfirmationPassword()).thenReturn(VALID_PASSWORD);
    }

    @Test
    public void testSupports() {
        assertTrue(validator.supports(RegistrationData.class));
        assertFalse(validator.supports(Object.class));
    }

    @Test
    public void testValidate_ValidData() {
        validator.validate(registrationData, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void testValidate_UsernameEmpty() {
        when(registrationData.getUsername()).thenReturn(EMPTY_LINE);

        validator.validate(registrationData, errors);

        verify(errors).rejectValue(fieldArgumentCaptor.capture(), errorCodeArgumentCaptor.capture());
        assertEquals(USERNAME_FIELD, fieldArgumentCaptor.getValue());
        assertEquals(USERNAME_EMPTY_ERROR_CODE, errorCodeArgumentCaptor.getValue());
    }

    @Test
    public void testValidate_PasswordEmpty() {
        when(registrationData.getPassword()).thenReturn(EMPTY_LINE);

        validator.validate(registrationData, errors);

        verify(errors).rejectValue(fieldArgumentCaptor.capture(), errorCodeArgumentCaptor.capture());
        assertEquals(PASSWORD_FIELD, fieldArgumentCaptor.getValue());
        assertEquals(PASSWORD_EMPTY_ERROR_CODE, errorCodeArgumentCaptor.getValue());
    }

    @Test
    public void testValidate_ConfirmationPasswordMismatch() {
        when(registrationData.getConfirmationPassword()).thenReturn(MISMATCHED_CONFIRMATION_PASSWORD);

        validator.validate(registrationData, errors);

        verify(errors).rejectValue(fieldArgumentCaptor.capture(), errorCodeArgumentCaptor.capture());
        assertEquals(CONFIRMATION_PASSWORD_FIELD, fieldArgumentCaptor.getValue());
        assertEquals(CONFIRMATION_PASSWORD_DOES_NOT_MATCH_ERROR_CODE, errorCodeArgumentCaptor.getValue());
    }
}
