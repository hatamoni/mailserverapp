package de.hixdev.mailserverapp.service;

import de.hixdev.mailserverapp.dto.EmailDto;
import java.util.List;

public interface EmailService {

  EmailDto saveEmail(EmailDto emailDto);

  EmailDto fetchEmail(Long emailId);

  List<EmailDto> fetchEmails();

  EmailDto updateEmail(EmailDto emailDto);

  boolean deleteEmail(Long emailId);

  List<EmailDto> saveAllEmails(List<EmailDto> emailsDto);

  List<EmailDto> updateAllEmails(List<EmailDto> emailsDto);

  boolean deleteAllEmails(List<EmailDto> emailsDto);
}
