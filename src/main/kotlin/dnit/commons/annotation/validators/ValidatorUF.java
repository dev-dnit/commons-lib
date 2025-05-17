package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidUF;
import dnit.commons.model.UF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public final class ValidatorUF implements ConstraintValidator<ValidUF, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Use @NotNull separately if needed
        }

        return UF.isUfValida(value);
    }

}