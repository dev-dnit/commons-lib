package dnit.commons.annotation.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ValidatorSNVStringTest {

    ValidatorSNVString validator = new ValidatorSNVString();



    @Test
    void shouldReturnTrue_whenValueIsNull() {
        boolean result = validator.isValid(null, mock(ConstraintValidatorContext.class));
        assertTrue(result, "Null values should be considered valid");
    }



    @Test
    void shouldReturnTrue_whenValueIsValidSNV() {
        // Test with various valid SNV formats
        assertTrue(validator.isValid("010BSP0100", mock(ConstraintValidatorContext.class)));
        assertTrue(validator.isValid("101ARJ0200", mock(ConstraintValidatorContext.class)));
        assertTrue(validator.isValid("230CMG0300", mock(ConstraintValidatorContext.class)));
        assertTrue(validator.isValid("364NRS0400", mock(ConstraintValidatorContext.class)));
        assertTrue(validator.isValid("490USC0500", mock(ConstraintValidatorContext.class)));
        assertTrue(validator.isValid("116VDF0600", mock(ConstraintValidatorContext.class)));
    }



    @Test
    void shouldReturnTrue_whenAllEixoTypesAreValid() {
        // Test all valid eixo types
        assertTrue(validator.isValid("116ASP0100", mock(ConstraintValidatorContext.class)));
        assertTrue(validator.isValid("116BSP0200", mock(ConstraintValidatorContext.class)));
        assertTrue(validator.isValid("116CSP0300", mock(ConstraintValidatorContext.class)));
        assertTrue(validator.isValid("116NSP0400", mock(ConstraintValidatorContext.class)));
        assertTrue(validator.isValid("116USP0500", mock(ConstraintValidatorContext.class)));
        assertTrue(validator.isValid("116VSP0600", mock(ConstraintValidatorContext.class)));
    }



    @Test
    void shouldReturnFalse_whenValueIsInvalidSNV() {
        // Test with empty string
        assertFalse(validator.isValid("", mock(ConstraintValidatorContext.class)));
        assertFalse(validator.isValid(" ", mock(ConstraintValidatorContext.class)));

        // Test with wrong length
        assertFalse(validator.isValid("116ASP01", mock(ConstraintValidatorContext.class))); // Too short
        assertFalse(validator.isValid("116ASP010000", mock(ConstraintValidatorContext.class))); // Too long

        // Test with invalid BR part
        assertFalse(validator.isValid("000ASP0100", mock(ConstraintValidatorContext.class))); // BR below range
        assertFalse(validator.isValid("999ASP0100", mock(ConstraintValidatorContext.class))); // BR above range
        assertFalse(validator.isValid("ABCASP0100", mock(ConstraintValidatorContext.class))); // Non-numeric BR

        // Test with invalid eixo type
        assertFalse(validator.isValid("116DSP0100", mock(ConstraintValidatorContext.class))); // D is not a valid eixo
        assertFalse(validator.isValid("1161SP0100", mock(ConstraintValidatorContext.class))); // Numeric eixo

        // Test with invalid UF
        assertFalse(validator.isValid("116A000100", mock(ConstraintValidatorContext.class))); // Invalid UF code
        assertFalse(validator.isValid("116AXX0100", mock(ConstraintValidatorContext.class))); // Non-existent UF

        // Test with invalid digitosTrecho
        assertFalse(validator.isValid("116ASPABCD", mock(ConstraintValidatorContext.class))); // Non-numeric digitosTrecho
    }


}
