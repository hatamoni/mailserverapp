package de.hixdev.mailserverapp.mapper;

import de.hixdev.mailserverapp.dto.EmailRecipientDto;
import java.util.List;

public class EmailRecipientMapper {

  private EmailRecipientMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static EmailRecipientDto mapToEmailRecipientDto(String email) {
    return new EmailRecipientDto(email);
  }

  public static String mapToEmailRecipient(List<EmailRecipientDto> emailList) {
    StringBuilder emailListString = new StringBuilder();
    for (EmailRecipientDto email : emailList) {
      if (email.getEmail() == null || email.getEmail().isEmpty()) {
        return "";
      }
      emailListString.append(email.getEmail() + ";");
    }
    return emailListString.toString();
  }

}
