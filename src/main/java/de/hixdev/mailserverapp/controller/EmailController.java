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
import de.hixdev.mailserverapp.dto.ErrorResponseDto;
import de.hixdev.mailserverapp.dto.ResponseDto;
import de.hixdev.mailserverapp.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
    name = "REST APIs to execute CRUD operations for Email Resource",
    description = "CRUD REST APIs - Create Email, Update Email (Send or Save Draft) , Get Email, Get All Emails, Delete Email"
)

@RestController
@RequestMapping(path = Constants.API_REQUESTMAPPING_PATH)
@AllArgsConstructor
@Validated
public class EmailController {

  private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

  private EmailService emailService;

  @Operation(
      summary = "Create an Email",
      description = "REST API to create a new Email"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "HTTP Status-Code for CREATED",
          content = @Content(
              schema = @Schema(implementation = EmailDto.class)
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status-Code for Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @PostMapping(API_PATH_CREATE)
  public ResponseEntity<EmailDto> saveEmail(@Valid @RequestBody EmailDto emailDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "saveEmail", API_PATH_CREATE);

    EmailDto savedEmail = emailService.saveEmail(emailDto);
    final ResponseEntity<EmailDto> emailDtoResponseEntity = new ResponseEntity<>(savedEmail,
        HttpStatus.CREATED);

    LOG.info(Constants.LOG_MESSAGE_END, "saveEmail", API_PATH_CREATE);
    return emailDtoResponseEntity;
  }

  @Operation(
      summary = "Get an Email with a given email-id",
      description = "REST API to fetch an Email with a given email-id"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status-Code for OK"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status-Code for Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @GetMapping(API_PATH_GET + "/" + PATHVARIABLE_EMAIL_ID)
  public ResponseEntity<EmailDto> getEmail(@PathVariable(EMAIL_ID) Long emailId) {
    LOG.info(Constants.LOG_MESSAGE_START, "fetchEmail", API_PATH_GET);

    EmailDto emailDto = emailService.fetchEmail(emailId);
    final ResponseEntity<EmailDto> emailDtoResponseEntity = new ResponseEntity<>(emailDto,
        HttpStatus.OK);

    LOG.info(Constants.LOG_MESSAGE_END, "fetchEmail", API_PATH_GET);

    return emailDtoResponseEntity;
  }

  @Operation(
      summary = "Fetch all Emails",
      description = "REST API to fetch all Emails"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status-Code for OK"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status-Code for Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @GetMapping(API_PATH_GET_ALL)
  public ResponseEntity<EmailsDto> getEmails() {
    LOG.info(Constants.LOG_MESSAGE_END, "getEmails", API_PATH_GET_ALL);

    List<EmailDto> emailDtoList = emailService.fetchEmails();
    final ResponseEntity<EmailsDto> emailsDtoResponseEntity = new ResponseEntity<>(
        new EmailsDto(emailDtoList), HttpStatus.OK);

    LOG.info(Constants.LOG_MESSAGE_END, "getEmails", API_PATH_GET_ALL);
    return emailsDtoResponseEntity;
  }

  @Operation(
      summary = "Update an Email",
      description = "REST API to update a new Email"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status-Code for CREATED"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status-Code for Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
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

  @Operation(
      summary = "Delete an Email with a given email-id",
      description = "REST API to delete an Email with the given email-id"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "417",
          description = "Expectation Failed"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
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

  @Operation(
      summary = "Create a list of Emails",
      description = "REST API to create Emails"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "HTTP Status-Code for CREATED"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status-Code for Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @PostMapping(API_PATH_CREATE_ALL)
  public ResponseEntity<EmailsDto> saveEmails(@Valid @RequestBody EmailsDto emailsDto) {
    LOG.info(Constants.LOG_MESSAGE_START, "saveEmails", API_PATH_CREATE_ALL);

    List<EmailDto> savedEmails = emailService.saveAllEmails(emailsDto.getEmails());
    final ResponseEntity<EmailsDto> emailsDtoResponseEntity = new ResponseEntity<>(
        new EmailsDto(savedEmails), HttpStatus.CREATED);

    LOG.info(Constants.LOG_MESSAGE_END, "saveEmails", API_PATH_CREATE_ALL);

    return emailsDtoResponseEntity;
  }

  @Operation(
      summary = "Bulk-Update for Emails",
      description = "REST API to update a list of Emails"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status-Code for CREATED"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status-Code for Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
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

  @Operation(
      summary = "Bulk-Delete for Emails",
      description = "REST API to delete a list of Emails"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "HTTP Status-Code for CREATED"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status-Code for Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
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

