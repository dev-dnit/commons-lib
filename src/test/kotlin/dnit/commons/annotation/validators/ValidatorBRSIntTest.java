package dnit.commons.annotation.validators;

import dnit.commons.exception.CommonException;
import dnit.commons.model.BR;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class ValidatorBRSIntTest {


    ValidatorBRInt validator = new ValidatorBRInt();


    @Test
    void shouldReturnTrue_whenValueIsNull() {
        boolean result = validator.isValid(null, mock(ConstraintValidatorContext.class));
        assertTrue(result, "Null values should be considered valid");
    }


    @Test
    void shouldReturnTrue_whenValueIsValidBR_int() {
        boolean result = validator.isValid(10, mock(ConstraintValidatorContext.class));
        assertTrue(result);
    }


    @Test
    void shouldReturnTrue_whenBRvalueIsInformed() {
        for (int i = 10; i <= 490; i += 50) {
            assertTrue(validator.isValid(i, mock(ConstraintValidatorContext.class)));
            assertTrue(validator.isValid(Integer.valueOf(BR.sanitizeBr(i)),
                                         mock(ConstraintValidatorContext.class)));
        }
    }


    @Test
    void shouldThrowException_whenValueIsInvalidBR() {
        assertThrows(CommonException.class, () -> validator.isValid(-1, mock(ConstraintValidatorContext.class)));
        assertThrows(CommonException.class, () -> validator.isValid(0, mock(ConstraintValidatorContext.class)));
        assertThrows(CommonException.class, () -> validator.isValid(1, mock(ConstraintValidatorContext.class)));
        assertThrows(CommonException.class, () -> validator.isValid(9, mock(ConstraintValidatorContext.class)));
        assertThrows(CommonException.class, () -> validator.isValid(900, mock(ConstraintValidatorContext.class)));
    }

}