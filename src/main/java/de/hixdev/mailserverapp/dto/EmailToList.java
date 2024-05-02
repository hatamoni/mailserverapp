package de.hixdev.mailserverapp.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailToListValidator.class)
public @interface EmailToList {

  String message() default "Email-To contains invalid email address";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
