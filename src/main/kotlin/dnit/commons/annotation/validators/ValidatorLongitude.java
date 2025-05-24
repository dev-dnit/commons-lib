package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidLongitude;
import dnit.commons.geo.ValidadorCoordenadas;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public final class ValidatorLongitude implements ConstraintValidator<ValidLongitude, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Use @NotNull separately if needed
        }

        return ValidadorCoordenadas.isValidLongitude(value);
    }

}