package de.hixdev.mailserverapp.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailCcListValidator.class)
public @interface EmailCcList {

  String message() default "Email-Cc contains invalid email address";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
