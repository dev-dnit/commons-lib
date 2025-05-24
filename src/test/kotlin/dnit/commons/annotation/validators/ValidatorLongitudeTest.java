package dnit.commons.annotation.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class ValidatorLongitudeTest {


    private ValidatorLongitude validator = new ValidatorLongitude();
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
    void testNegative180ValueIsValid() {
        assertTrue(validator.isValid(-180.0, context), "Valor -180 deve ser válido");
    }

    @Test
    void testPositive180ValueIsValid() {
        assertTrue(validator.isValid(180.0, context), "Valor 180 deve ser válido");
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
    void testBelowNegative180ValueIsInvalid() {
        assertFalse(validator.isValid(-180.1, context), "Valor abaixo de -180 deve ser inválido");
    }

    @Test
    void testAbove180ValueIsInvalid() {
        assertFalse(validator.isValid(180.1, context), "Valor acima de 180 deve ser inválido");
    }

}