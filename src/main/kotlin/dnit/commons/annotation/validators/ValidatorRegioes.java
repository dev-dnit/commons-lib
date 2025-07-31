package dnit.commons.annotation.validators;

import dnit.commons.annotation.ValidRegioes;
import dnit.commons.model.Regiao;
import dnit.commons.utils.CollectionUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;


public final class ValidatorRegioes implements ConstraintValidator<ValidRegioes, List<String>> {

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (CollectionUtils.isNullOrEmpty(values)) {
            return true; // null lists are valid, use @NotNull if needed
        }

        for (String value : values) {
            if (!Regiao.isRegiaoValida(value)) {
                return false; // invalid Regiao
            }
        }

        return true;
    }

}