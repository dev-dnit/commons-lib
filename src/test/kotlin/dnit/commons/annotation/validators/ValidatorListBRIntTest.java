package dnit.commons.annotation.validators;

import dnit.commons.exception.CommonException;
import dnit.commons.model.BR;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class ValidatorListBRIntTest {


    ValidatorListBRInt validator = new ValidatorListBRInt();


    @Test
    void shouldReturnTrue_whenValueIsNull() {
        boolean result = validator.isValid(null, mock(ConstraintValidatorContext.class));
        assertTrue(result, "Null values should be considered valid");
    }


    @Test
    void shouldReturnTrue_whenValueIsEmpty() {
        boolean result = validator.isValid(Collections.emptyList(), mock(ConstraintValidatorContext.class));
        assertTrue(result, "Empty values should be considered valid");
    }


    @Test
    void shouldReturnTrue_whenValueIsValidBR_int() {
        boolean result = validator.isValid(List.of(10), mock(ConstraintValidatorContext.class));
        assertTrue(result);
    }


    @Test
    void shouldReturnTrue_whenBRvalueIsInformed() {
        List<Integer> accumulatedInt = new ArrayList<>();
        for (int i = 10; i <= 490; i += 50) {
            assertTrue(validator.isValid(List.of(i), mock(ConstraintValidatorContext.class)));
            assertTrue(validator.isValid(List.of(Integer.valueOf(BR.sanitizeBr(i))), mock(ConstraintValidatorContext.class)));
            accumulatedInt.add(i);
        }

        assertTrue(validator.isValid(accumulatedInt, mock(ConstraintValidatorContext.class)));
    }


    @Test
    void shouldThrowException_whenValueIsInvalidBR() {
        assertThrows(CommonException.class, () -> validator.isValid(List.of(-1), mock(ConstraintValidatorContext.class)));
        assertThrows(CommonException.class, () -> validator.isValid(List.of(0), mock(ConstraintValidatorContext.class)));
        assertThrows(CommonException.class, () -> validator.isValid(List.of(1), mock(ConstraintValidatorContext.class)));
        assertThrows(CommonException.class, () -> validator.isValid(List.of(9), mock(ConstraintValidatorContext.class)));
        assertThrows(CommonException.class, () -> validator.isValid(List.of(900), mock(ConstraintValidatorContext.class)));
    }

}