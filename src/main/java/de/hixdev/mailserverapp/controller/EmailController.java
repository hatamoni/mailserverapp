package de.hixdev.mailserverapp.controller;

import static de.hixdev.mailserverapp.constants.Constants.API_PATH_CREATE;
import static de.hixdev.mailserverapp.constants.Constants.API_PATH_CREATE_ALL;
import static de.hixdev.mailserverapp.constants.Constants.API_PATH_DELETE;
import static de.hixdev.mailserverapp.constants.Constants.API_PATH_DELETE_ALL;
import static de.hixdev.mailserverapp.constants.Constants.API_PATH_GET;
import static de.hixdev.mailserverapp.constants.Constants.API_PATH_GET_ALL;
import static de.hixdev.mailserverapp.constants.Constants.API_PATH_UPDATE;
import static de.hixdev.mailserverapp.constants.Constants.API_PATH_UPDATE_ALL;
import static de.hixdev.mailserverapp.constants.Constants.EMAIL_ID;
import static de.hixdev.mailserverapp.constants.Constants.PATHVARIABLE_EMAIL_ID;

import de.hixdev.mailserverapp.constants.Constants;
import de.hixdev.mailserverapp.dto.EmailDto;
import de.hixdev.mailserverapp.dto.EmailsDto;
import de.hixdev.mailserverapp.dto.ResponseDto;
import de.hixdev.mailserverapp.service.EmailService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = Constants.API_REQUESTMAPPING_PATH)
@AllArgsConstructor
@Validated
public class EmailController {

  private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

  private EmailService emailService;

  @PostMapping(API_PATH_CREATE)
  public ResponseEntity<EmailDto> saveEmail(@Valid @RequestBody EmailDto emailDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "saveEmail", API_PATH_CREATE);

    EmailDto savedEmail = emailService.saveEmail(emailDto);
    final ResponseEntity<EmailDto> emailDtoResponseEntity = new ResponseEntity<>(savedEmail,
        HttpStatus.CREATED);

    LOG.info(Constants.LOG_MESSAGE_END, "saveEmail", API_PATH_CREATE);
    return emailDtoResponseEntity;
  }

  @GetMapping(API_PATH_GET + "/" + PATHVARIABLE_EMAIL_ID)
  public ResponseEntity<EmailDto> getEmail(@PathVariable(EMAIL_ID) Long emailId) {
    LOG.info(Constants.LOG_MESSAGE_START, "fetchEmail", API_PATH_GET);

    EmailDto emailDto = emailService.fetchEmail(emailId);
    final ResponseEntity<EmailDto> emailDtoResponseEntity = new ResponseEntity<>(emailDto,
        HttpStatus.OK);

    LOG.info(Constants.LOG_MESSAGE_END, "fetchEmail", API_PATH_GET);

    return emailDtoResponseEntity;
  }

  @GetMapping(API_PATH_GET_ALL)
  public ResponseEntity<EmailsDto> getEmails() {
    LOG.info(Constants.LOG_MESSAGE_END, "getEmails", API_PATH_GET_ALL);

    List<EmailDto> emailDtoList = emailService.fetchEmails();
    final ResponseEntity<EmailsDto> emailsDtoResponseEntity = new ResponseEntity<>(
        new EmailsDto(emailDtoList), HttpStatus.OK);

    LOG.info(Constants.LOG_MESSAGE_END, "getEmails", API_PATH_GET_ALL);
    return emailsDtoResponseEntity;
  }

  @PutMapping(API_PATH_UPDATE)
  public ResponseEntity<ResponseDto> updateEmail(@Valid @RequestBody EmailDto emailDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "updateEmail", API_PATH_UPDATE);
    EmailDto updatedEmail = emailService.updateEmail(emailDto);
    if (updatedEmail != null) {
      LOG.info(Constants.LOG_MESSAGE_END, "updateEmail succeeded", API_PATH_UPDATE);
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(Constants.HTTP_STATUS_CODE_200,
              Constants.HTTP_STATUS_CODE_200_MESSAGE));
    } else {
      LOG.info(Constants.LOG_MESSAGE_END, "updateEmail failed", API_PATH_UPDATE);
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(Constants.HTTP_STATUS_CODE_420,
              Constants.HTTP_STATUS_CODE_420_MESSAGE_UPDATE));
    }
  }

  @DeleteMapping(API_PATH_DELETE + "/" + PATHVARIABLE_EMAIL_ID)
  public ResponseEntity<ResponseDto> deleteEmail(@PathVariable(EMAIL_ID) Long emailId) {
    LOG.info(Constants.LOG_MESSAGE_START, "deleteEmail", API_PATH_DELETE);

    boolean isDeleted = emailService.deleteEmail(emailId);
    if (isDeleted) {
      LOG.info(Constants.LOG_MESSAGE_END, "deleteEmail succeeded", API_PATH_DELETE);
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(Constants.HTTP_STATUS_CODE_200,
              Constants.HTTP_STATUS_CODE_200_MESSAGE));
    } else {
      LOG.info(Constants.LOG_MESSAGE_END, "deleteEmail failed", API_PATH_DELETE);
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(Constants.HTTP_STATUS_CODE_420,
              Constants.HTTP_STATUS_CODE_420_MESSAGE_DELETE));
    }
  }

  @PostMapping(API_PATH_CREATE_ALL)
  public ResponseEntity<EmailsDto> saveEmails(@Valid @RequestBody EmailsDto emailsDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "saveEmails", API_PATH_CREATE_ALL);

    List<EmailDto> savedEmails = emailService.saveAllEmails(emailsDto.getEmails());
    final ResponseEntity<EmailsDto> emailsDtoResponseEntity = new ResponseEntity<>(
        new EmailsDto(savedEmails), HttpStatus.CREATED);

    LOG.info(Constants.LOG_MESSAGE_END, "saveEmails", API_PATH_CREATE_ALL);

    return emailsDtoResponseEntity;
  }

  @PutMapping(API_PATH_UPDATE_ALL)
  public ResponseEntity<ResponseDto> updateEmails(@Valid @RequestBody EmailsDto emailsDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "updateEmails", API_PATH_UPDATE_ALL);

    List<EmailDto> savedEmails = emailService.updateAllEmails(emailsDto.getEmails());
    if (savedEmails != null && !savedEmails.isEmpty()) {
      LOG.info(Constants.LOG_MESSAGE_END, "saveEmails succeeded", API_PATH_UPDATE_ALL);
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(Constants.HTTP_STATUS_CODE_200,
              Constants.HTTP_STATUS_CODE_200_MESSAGE));
    } else {
      LOG.info(Constants.LOG_MESSAGE_END, "saveEmails failed", API_PATH_UPDATE_ALL);
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(Constants.HTTP_STATUS_CODE_420,
              Constants.HTTP_STATUS_CODE_420_MESSAGE_UPDATE));
    }
  }

  @DeleteMapping(API_PATH_DELETE_ALL)
  public ResponseEntity<ResponseDto> deleteEmails(@Valid @RequestBody EmailsDto emailsDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "deleteEmails", API_PATH_UPDATE_ALL);
    boolean isDeleted = emailService.deleteAllEmails(emailsDto.getEmails());
    if (isDeleted) {
      LOG.info(Constants.LOG_MESSAGE_END, "deleteEmails succeeded", API_PATH_DELETE_ALL);

      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(Constants.HTTP_STATUS_CODE_200,
              Constants.HTTP_STATUS_CODE_200_MESSAGE));
    } else {
      LOG.info(Constants.LOG_MESSAGE_END, "deleteEmails failed", API_PATH_DELETE_ALL);
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(Constants.HTTP_STATUS_CODE_420,
              Constants.HTTP_STATUS_CODE_420_MESSAGE_DELETE));
    }
  }

}

