package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidBR;
import dnit.commons.model.BR;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public final class ValidatorBRInt implements ConstraintValidator<ValidBR, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Use @NotNull separately if needed
        }

        return BR.isValidBR(BR.sanitizeBr(value));
    }

}