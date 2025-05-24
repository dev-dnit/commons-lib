package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidBRs;
import dnit.commons.model.BR;
import dnit.commons.utils.CollectionUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;


public final class ValidatorListBRString implements ConstraintValidator<ValidBRs, List<String>> {

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (CollectionUtils.isNullOrEmpty(values)) {
            return true; // null lists are valid, use @NotNull if needed
        }

        for (String value : values) {
            if (value == null) {
                return false;
            }

            if (!BR.isValidBR(value)) {
                return false; // invalid BR
            }
        }

        return true;
    }

}