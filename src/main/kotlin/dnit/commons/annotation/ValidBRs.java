package dnit.commons.annotation;

import dnit.commons.annotation.validators.ValidatorListBRInt;
import dnit.commons.annotation.validators.ValidatorListBRString;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy = {ValidatorListBRString.class, ValidatorListBRInt.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidBRs {


    /**
     * The message that will be returned when the validation fails.
     */
    String message() default "Invalid BRs";


    /**
     * The groups the constraint belongs to.
     */
    Class<?>[] groups() default {};


    /**
     * Additional data that can be used by the validation framework.
     */
    Class<? extends Payload>[] payload() default {};


}