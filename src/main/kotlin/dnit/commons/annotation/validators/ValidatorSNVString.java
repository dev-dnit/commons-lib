package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidSNV;
import dnit.commons.model.SNV;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public final class ValidatorSNVString implements ConstraintValidator<ValidSNV, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Use @NotNull separately if needed
        }

        return SNV.isValidSNV(value);
    }

}