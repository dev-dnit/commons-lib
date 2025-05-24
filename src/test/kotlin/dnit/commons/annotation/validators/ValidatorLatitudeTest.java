package dnit.commons.annotation.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class ValidatorLatitudeTest {


    private ValidatorLatitude validator = new ValidatorLatitude();
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
    void testNegative90ValueIsValid() {
        assertTrue(validator.isValid(-90.0, context), "Valor -90 deve ser válido");
    }

    @Test
    void testPositive90ValueIsValid() {
        assertTrue(validator.isValid(90.0, context), "Valor 90 deve ser válido");
    }

    @Test
    void test500ValueIsInvalid() {
        assertFalse(validator.isValid(500.0, context), "Valor 500 deve ser inválido");
    }

    @Test
    void test10000ValueIsInvalid() {
        assertFalse(validator.isValid(10000.0, context), "Valor 10000 deve ser inválido");
    }

    @Test
    void testBelowNegative90ValueIsInvalid() {
        assertFalse(validator.isValid(-90.1, context), "Valor abaixo de -90 deve ser inválido");
    }

    @Test
    void testAbove90ValueIsInvalid() {
        assertFalse(validator.isValid(90.1, context), "Valor acima de 90 deve ser inválido");
    }

}