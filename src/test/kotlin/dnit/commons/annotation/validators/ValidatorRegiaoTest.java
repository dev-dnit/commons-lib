package dnit.commons.annotation.validators;

import dnit.commons.model.Regiao;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class ValidatorRegiaoTest {


    ValidatorRegiao validator = new ValidatorRegiao();


    @Test
    void shouldReturnTrue_whenValueIsNull() {
        boolean result = validator.isValid(null, mock(ConstraintValidatorContext.class));
        assertTrue(result, "Null values should be considered valid");
    }


    @Test
    void shouldReturnTrue_whenValueIsValidRegiao() {
        boolean result = validator.isValid("Nordeste", mock(ConstraintValidatorContext.class));
        assertTrue(result);
    }


    @Test
    void shouldReturnTrue_whenRegiaovalueIsInformed() {
        for (Regiao regiao : Regiao.values()) {
            boolean result = validator.isValid(regiao.nomeCompleto, mock(ConstraintValidatorContext.class));
            assertTrue(result, "Regiao should be valid: " + regiao.nomeCompleto);
        }
    }


    @Test
    void shouldReturnFalse_whenValueIsInvalidRegiao() {
        boolean result = validator.isValid("XX", mock(ConstraintValidatorContext.class));
        assertFalse(result);
    }

}