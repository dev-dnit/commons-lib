package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidBRs;
import dnit.commons.model.BR;
import dnit.commons.utils.CollectionUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;


public final class ValidatorListBRInt implements ConstraintValidator<ValidBRs, List<Integer>> {

    @Override
    public boolean isValid(List<Integer> values, ConstraintValidatorContext context) {
        if (CollectionUtils.isNullOrEmpty(values)) {
            return true; // null lists are valid, use @NotNull if needed
        }

        for (Integer value : values) {
            if (value == null) {
                return false;
            }

            if (!BR.isValidBR(BR.sanitizeBr(value))) {
                return false; // invalid BR
            }
        }

        return true;
    }

}