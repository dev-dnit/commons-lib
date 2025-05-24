package dnit.commons.annotation.validators;

import dnit.commons.model.UF;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class ValidatorUFsTest {


    ValidatorUFs validator = new ValidatorUFs();


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
    void shouldReturnTrue_whenValueIsValidUf() {
        boolean result = validator.isValid(List.of("SP"), mock(ConstraintValidatorContext.class));
        assertTrue(result);
    }


    @Test
    void shouldReturnTrue_whenUFvalueIsInformed() {
        for (UF uf : UF.values()) {
            boolean result = validator.isValid(List.of(uf.sigla), mock(ConstraintValidatorContext.class));
            assertTrue(result, "UF " + uf + " should be valid");
        }
    }


    @Test
    void shouldReturnFalse_whenValueIsInvalidUf() {
        boolean result = validator.isValid(List.of("XX"), mock(ConstraintValidatorContext.class));
        assertFalse(result);
    }

}