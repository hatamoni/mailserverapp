package de.hixdev.mailserverapp.mapper;

import de.hixdev.mailserverapp.dto.EmailDto;
import de.hixdev.mailserverapp.dto.EmailRecipientDto;
import de.hixdev.mailserverapp.entity.Email;
import java.util.Collections;
import java.util.List;

public class EmailMapper {

  private EmailMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static EmailDto mapToEmailDto(Email email) {

    return  new EmailDto(
        email.getEmailId(),
        email.getEmailFrom(),
        mapToEmailRecipientDtoList(email.getEmailTo()),
        email.getEmailCc() == null || email.getEmailCc().isEmpty() ? Collections.emptyList()
            : mapToEmailRecipientDtoList(email.getEmailCc()),
        email.getEmailBody(),
        email.getState(),
        email.getCreatedDate(),
        email.getCreatedBy(),
        email.getLastModifiedDate(),
        email.getLastModifiedBy()
    );
  }

  static List<EmailRecipientDto> mapToEmailRecipientDtoList(String emails) {
    List<String> emailList = List.of(emails.split(";"));
    return emailList.stream().map(EmailRecipientMapper::mapToEmailRecipientDto).toList();
  }


  public static Email mapToEmail(EmailDto emailDto) {
    return new Email(
        emailDto.getEmailId(),
        emailDto.getEmailFrom(),
        EmailRecipientMapper.mapToEmailRecipient(emailDto.getEmailTo()),
        emailDto.getEmailCc() == null || emailDto.getEmailCc().isEmpty() ? ""
            : EmailRecipientMapper.mapToEmailRecipient(emailDto.getEmailCc()),
        emailDto.getEmailBody(),
        emailDto.getState(),
        emailDto.getCreatedDate(),
        emailDto.getCreatedBy(),
        emailDto.getLastModifiedDate(),
        emailDto.getLastModifiedBy());
  }

}

