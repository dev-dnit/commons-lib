package dnit.commons.annotation.validators;

import dnit.commons.model.BR;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class ValidatorListBRStringTest {


    ValidatorListBRString validator = new ValidatorListBRString();


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
    void shouldReturnTrue_whenValueIsValidBR_string() {
        boolean result = validator.isValid(List.of("010"), mock(ConstraintValidatorContext.class));
        assertTrue(result);
    }


    @Test
    void shouldReturnTrue_whenMultipleBRvalueIsInformed() {
        assertTrue(validator.isValid( List.of("010", "020", "030", "040", "050", "060", "070", "080", "090", "100"),
                                      mock(ConstraintValidatorContext.class)));
    }


    @Test
    void shouldReturnTrue_whenBRvalueIsInformed() {
        List<String> accumulatedStr = new ArrayList<>();
        for (int i = 10; i <= 490; i += 50) {
            assertTrue(validator.isValid(List.of(BR.sanitizeBr(i)), mock(ConstraintValidatorContext.class)));

            String formatted = String.format("%03d", i);
            assertTrue(validator.isValid(List.of(formatted), mock(ConstraintValidatorContext.class)));
            assertTrue(validator.isValid(List.of(BR.sanitizeBr(formatted)), mock(ConstraintValidatorContext.class)));

            accumulatedStr.add(formatted);
        }

        assertTrue(validator.isValid(accumulatedStr, mock(ConstraintValidatorContext.class)));
    }


    @Test
    void shouldReturnFalse_whenValueIsInvalidBR() {
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