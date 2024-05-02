package de.hixdev.mailserverapp.service.impl;

import static de.hixdev.mailserverapp.constants.Constants.EMAIL;
import static de.hixdev.mailserverapp.constants.Constants.EMAIL_ID;

import de.hixdev.mailserverapp.constants.Constants;
import de.hixdev.mailserverapp.dto.EmailDto;
import de.hixdev.mailserverapp.entity.Email;
import de.hixdev.mailserverapp.entity.State;
import de.hixdev.mailserverapp.exception.EmailUpdateNotPossibleException;
import de.hixdev.mailserverapp.exception.ResourceNotFoundException;
import de.hixdev.mailserverapp.mapper.EmailMapper;
import de.hixdev.mailserverapp.repository.EmailRepository;
import de.hixdev.mailserverapp.service.EmailService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

  private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);


  private EmailRepository emailRepository;

  @Override
  public EmailDto saveEmail(EmailDto emailDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "saveEmail", emailDto.getEmailId());

    Email email = EmailMapper.mapToEmail(emailDto);
    Email savedEmail = emailRepository.save(email);
    LOG.info(Constants.LOG_MESSAGE, "Email was successfully created");

    EmailDto savedEmailDto = EmailMapper.mapToEmailDto(savedEmail);

    LOG.info(Constants.LOG_MESSAGE_END, "saveEmail", "");

    return savedEmailDto;
  }

  @Override
  public EmailDto fetchEmail(Long emailId) {
    LOG.info(Constants.LOG_MESSAGE_START, "fetchEmail", emailId);

    Email email = emailRepository.findEmailByEmailId(emailId).orElseThrow(
        () -> new ResourceNotFoundException(EMAIL, EMAIL_ID, emailId)
    );

    EmailDto emailDto = EmailMapper.mapToEmailDto(email);

    LOG.info(Constants.LOG_MESSAGE_END, "fetchEmail", "");

    return emailDto;
  }

  @Override
  public List<EmailDto> fetchEmails() {
    LOG.info(Constants.LOG_MESSAGE_START, "fetchEmails", "");

    List<Email> emails = emailRepository.findAll();
    final List<EmailDto> emailDtoList = emails.stream().map(EmailMapper::mapToEmailDto)
        .toList();

    LOG.info(Constants.LOG_MESSAGE_END, "fetchEmails", "");

    return emailDtoList;
  }

  @Override
  public EmailDto updateEmail(EmailDto emailDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "updateEmail", " Email-ID: " + emailDto.getEmailId());

    validateIfEmailCanBeUpdated(emailDto);

    Email email = EmailMapper.mapToEmail(emailDto);
    Email savedEmail = emailRepository.save(email);
    EmailDto savedEmailDto = EmailMapper.mapToEmailDto(savedEmail);

    LOG.info(Constants.LOG_MESSAGE_END, "updateEmail", "");

    return savedEmailDto;
  }

  private boolean validateIfEmailCanBeUpdated(EmailDto emailDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "validateIfEmailCanBeUpdated", " - Email-ID: " + emailDto.getEmailId());

    if(emailDto.getEmailId() != null) {
      Email email = emailRepository.findEmailByEmailId(emailDto.getEmailId()).orElseThrow(
          () -> new ResourceNotFoundException("Email", "emailId", emailDto.getEmailId())
      );
      if (email.getState() != null
          && email.getState().compareTo(State.EMAIL_DRAFT.getCode()) != 0) {
        LOG.debug(Constants.LOG_MESSAGE, "Email can not be updated");
        throw new EmailUpdateNotPossibleException("Email", emailDto.getEmailId(),"state", emailDto.getState());
      }
    }

    LOG.info(Constants.LOG_MESSAGE_END, "validateIfEmailCanBeUpdated", "");

    return true;
  }

  @Override
  public boolean deleteEmail(Long emailId) {
    LOG.info(Constants.LOG_MESSAGE_START, "deleteEmail", " - Email-ID: %s " , emailId);

    Email email = emailRepository.findEmailByEmailId(emailId).orElseThrow(
        () -> new ResourceNotFoundException(EMAIL, EMAIL_ID, emailId)
    );
    emailRepository.deleteById(email.getEmailId());

    LOG.info(Constants.LOG_MESSAGE_END, "deleteEmail", "");
    return true;
  }

  @Override
  public List<EmailDto> saveAllEmails(List<EmailDto> emailsDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "saveAllEmails", "");

    final List<EmailDto> emailDtoList = emailRepository.saveAll(
            emailsDto.stream().map(EmailMapper::mapToEmail).toList())
        .stream().map(EmailMapper::mapToEmailDto).toList();

    LOG.info(Constants.LOG_MESSAGE_END, "saveAllEmails", "");

    return emailDtoList;
  }

  @Override
  public List<EmailDto> updateAllEmails(List<EmailDto> emailsDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "updateAllEmails", "");

    emailsDto.stream().forEach(emailDto -> validateIfEmailCanBeUpdated(emailDto));
    final List<EmailDto> emailDtoList = emailRepository.saveAll(
            emailsDto.stream().map(EmailMapper::mapToEmail).toList())
        .stream().map(EmailMapper::mapToEmailDto).toList();

    LOG.info(Constants.LOG_MESSAGE_END, "updateAllEmails", "");

    return emailDtoList;
  }

  @Override
  public boolean deleteAllEmails(List<EmailDto> emailsDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "deleteAllEmails", "");

    for (EmailDto emailDto : emailsDto) {
      emailRepository.findEmailByEmailId(emailDto.getEmailId()).orElseThrow(
          () -> new ResourceNotFoundException(EMAIL, EMAIL_ID, emailDto.getEmailId())
      );
    }
    emailRepository.deleteAll(
        emailsDto.stream().map(EmailMapper::mapToEmail).toList());

    LOG.info(Constants.LOG_MESSAGE_END, "deleteAllEmails", "");
    return true;
  }

}
