package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidUFs;
import dnit.commons.model.UF;
import dnit.commons.utils.CollectionUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;


public class ValidatorUFs implements ConstraintValidator<ValidUFs, List<String>> {

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (CollectionUtils.isNullOrEmpty(values)) {
            return true; // null lists are valid, use @NotNull if needed
        }

        for (String value : values) {
            if (!UF.isUfValida(value)) {
                return false; // invalid UF
            }
        }

        return true;
    }

}