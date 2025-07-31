package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidSNVs;
import dnit.commons.model.SNV;
import dnit.commons.utils.CollectionUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;


public final class ValidatorListSNV implements ConstraintValidator<ValidSNVs, List<String>> {

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (CollectionUtils.isNullOrEmpty(values)) {
            return true; // null lists are valid, use @NotNull if needed
        }

        for (String value : values) {
            if (value == null) {
                return false;
            }

            if (!SNV.isValidSNV(value)) {
                return false; // invalid SNV
            }
        }

        return true;
    }

}