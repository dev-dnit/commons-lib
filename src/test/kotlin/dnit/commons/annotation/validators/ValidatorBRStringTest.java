package dnit.commons.annotation.validators;

import dnit.commons.model.BR;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class ValidatorBRStringTest {


    ValidatorBRString validator = new ValidatorBRString();


    @Test
    void shouldReturnTrue_whenValueIsNull() {
        boolean result = validator.isValid(null, mock(ConstraintValidatorContext.class));
        assertTrue(result, "Null values should be considered valid");
    }


    @Test
    void shouldReturnTrue_whenValueIsValidBR_string() {
        boolean result = validator.isValid("010", mock(ConstraintValidatorContext.class));
        assertTrue(result);
    }

    @Test
    void shouldReturnTrue_whenBRvalueIsInformed() {
        for (int i = 10; i <= 490; i += 50) {
            String formatted = String.format("%03d", i);
            assertTrue(validator.isValid(formatted, mock(ConstraintValidatorContext.class)));
            assertTrue(validator.isValid(BR.sanitizeBr(formatted), mock(ConstraintValidatorContext.class)));
        }
    }


    @Test
    void shouldReturnFalse_whenValueIsInvalidBR() {
        assertFalse(validator.isValid("invalid", mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid("XX", mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid("001", mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid("-1", mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid("0", mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid("1", mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid("9", mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid("1000", mock(ConstraintValidatorContext.class)));
    }

}