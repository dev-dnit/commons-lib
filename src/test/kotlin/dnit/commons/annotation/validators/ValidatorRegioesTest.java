package dnit.commons.annotation.validators;

import dnit.commons.model.Regiao;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class ValidatorRegioesTest {


    ValidatorRegioes validator = new ValidatorRegioes();


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
    void shouldReturnTrue_whenValueIsValidRegiao_string() {
        boolean result = validator.isValid(List.of("Nordeste"), mock(ConstraintValidatorContext.class));
        assertTrue(result);
    }


    @Test
    void shouldReturnTrue_whenMultipleRegiaovalueIsInformed() {
        assertTrue(validator.isValid(Arrays.stream(Regiao.values()).map(c -> c.nomeCompleto).toList(), mock(ConstraintValidatorContext.class)));
    }


    @Test
    void shouldReturnFalse_whenValueIsInvalidRegiao() {
        assertFalse(validator.isValid(List.of("invalid"), mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid(List.of("XX"), mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid(List.of("001"), mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid(List.of("-1"), mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid(List.of("0"), mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid(List.of("1"), mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid(List.of("9"), mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid(List.of("1000"), mock(ConstraintValidatorContext.class)));
    }

}