package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidBR;
import dnit.commons.model.BR;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public final class ValidatorBRString implements ConstraintValidator<ValidBR, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Use @NotNull separately if needed
        }

        return BR.isValidBR(value);
    }

}