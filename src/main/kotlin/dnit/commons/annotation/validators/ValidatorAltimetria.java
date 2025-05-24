package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidAltimetria;
import dnit.commons.geo.ValidadorCoordenadas;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public final class ValidatorAltimetria implements ConstraintValidator<ValidAltimetria, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Use @NotNull separately if needed
        }

        return ValidadorCoordenadas.isValidAltimetria(value);
    }

}