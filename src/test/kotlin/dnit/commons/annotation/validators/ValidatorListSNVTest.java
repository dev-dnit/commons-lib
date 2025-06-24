package dnit.commons.annotation.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ValidatorListSNVTest {


    ValidatorListSNV validator = new ValidatorListSNV();


    @Test
    void shouldReturnTrue_whenValueIsNull() {
        boolean result = validator.isValid(null, mock(ConstraintValidatorContext.class));
        assertTrue(result, "Null lists should be considered valid");
    }



    @Test
    void shouldReturnTrue_whenValueIsEmptyList() {
        boolean result = validator.isValid(Collections.emptyList(), mock(ConstraintValidatorContext.class));
        assertTrue(result, "Empty lists should be considered valid");
    }



    @Test
    void shouldReturnTrue_whenAllValuesAreValidSNVs() {
        List<String> validSNVs = Arrays.asList(
            "010BSP0100",
            "101ARJ0200",
            "230CMG0300",
            "364NRS0400",
            "490USC0500",
            "116VDF0600"
        );

        boolean result = validator.isValid(validSNVs, mock(ConstraintValidatorContext.class));
        assertTrue(result, "List with all valid SNVs should be valid");
    }



    @Test
    void shouldReturnTrue_whenAllEixoTypesAreValid() {
        List<String> validEixoTypes = Arrays.asList(
            "116ASP0100",
            "116BSP0200",
            "116CSP0300",
            "116NSP0400",
            "116USP0500",
            "116VSP0600"
        );

        boolean result = validator.isValid(validEixoTypes, mock(ConstraintValidatorContext.class));
        assertTrue(result, "List with all valid eixo types should be valid");
    }



    @Test
    void shouldReturnFalse_whenListContainsNullValue() {
        List<String> listWithNull = Arrays.asList("116ASP0100", null, "230CMG0300");

        boolean result = validator.isValid(listWithNull, mock(ConstraintValidatorContext.class));
        assertFalse(result, "List containing null values should be invalid");
    }



    @Test
    void shouldReturnFalse_whenListContainsInvalidSNV() {
        // Test with various invalid SNVs
        List<String> invalidSNVs = Arrays.asList(
            "116ASP0100", // Valid
            "000ASP0100", // Invalid BR
            "116ASP01"    // Wrong length
        );

        boolean result = validator.isValid(invalidSNVs, mock(ConstraintValidatorContext.class));
        assertFalse(result, "List containing invalid SNVs should be invalid");

        // Test with invalid eixo
        List<String> invalidEixo = Arrays.asList(
            "116ASP0100", // Valid
            "116DSP0100"  // Invalid eixo
        );

        result = validator.isValid(invalidEixo, mock(ConstraintValidatorContext.class));
        assertFalse(result, "List containing SNVs with invalid eixo should be invalid");

        // Test with invalid UF
        List<String> invalidUF = Arrays.asList(
            "116ASP0100", // Valid
            "116AXX0100"  // Invalid UF
        );

        result = validator.isValid(invalidUF, mock(ConstraintValidatorContext.class));
        assertFalse(result, "List containing SNVs with invalid UF should be invalid");

        // Test with invalid digitosTrecho
        List<String> invalidDigitosTrecho = Arrays.asList(
            "116ASP0100", // Valid
            "116ASPABCD"  // Invalid digitosTrecho
        );

        result = validator.isValid(invalidDigitosTrecho, mock(ConstraintValidatorContext.class));
        assertFalse(result, "List containing SNVs with invalid digitosTrecho should be invalid");
    }
}
