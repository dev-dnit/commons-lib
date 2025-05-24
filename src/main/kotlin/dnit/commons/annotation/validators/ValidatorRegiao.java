package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidRegiao;
import dnit.commons.model.Regiao;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public final class ValidatorRegiao implements ConstraintValidator<ValidRegiao, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Use @NotNull separately if needed
        }

        return Regiao.isRegiaoValida(value);
    }

}