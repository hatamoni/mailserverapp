package de.hixdev.mailserverapp.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class EmailCcListValidator implements
    ConstraintValidator<EmailCcList, List<EmailRecipientDto>> {

  private static final String EMAIL_REGEX = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

  @Override
  public void initialize(EmailCcList constraintAnnotation) {
  // SonarLint reports bad practice of empty methods, but this is a necessary override
  }

  @Override
  public boolean isValid(List<EmailRecipientDto> emailList, ConstraintValidatorContext context) {
    if (emailList == null || emailList.isEmpty()) {
      return true; // Empty list is not valid
    }

    for (EmailRecipientDto email : emailList) {
      if (email.getEmail() == null || email.getEmail().isEmpty()) {
        return false; // Empty email address found
      }

      if (!email.getEmail().matches(EMAIL_REGEX)) {
        return false; // Invalid email address found
      }
    }

    return true; // All email addresses are valid
  }
}
