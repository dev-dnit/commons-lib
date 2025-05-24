package dnit.commons.annotation.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class ValidatorAltimetriaTest {

    private ValidatorAltimetria validator = new ValidatorAltimetria();
    private final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);


    @Test
    void testNullValueIsValid() {
        assertTrue(validator.isValid(null, context), "Valor null deve ser válido");
    }

    @Test
    void testZeroValueIsValid() {
        assertTrue(validator.isValid(0.0, context), "Valor 0 deve ser válido");
    }

    @Test
    void test500ValueIsValid() {
        assertTrue(validator.isValid(500.0, context), "Valor 500 deve ser válido");
    }

    @Test
    void test10000ValueIsValid() {
        assertTrue(validator.isValid(10000.0, context), "Valor 10000 deve ser válido");
    }

    @Test
    void testNegativeValueIsInvalid() {
        assertTrue(validator.isValid(-1.0, context), "Valor negativo deve ser válido");
    }

    @Test
    void testNegativeLimitIsInvalid() {
        assertFalse(validator.isValid(-500.1, context), "Valor negativo < -500 deve ser inválido");
    }

    @Test
    void testAbove10000ValueIsInvalid() {
        assertFalse(validator.isValid(10000.1, context), "Valor acima de 10000 deve ser inválido");
    }

}