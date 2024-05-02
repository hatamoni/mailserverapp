package de.hixdev.mailserverapp.service.impl;

import static de.hixdev.mailserverapp.data.TestData.CREATED_BY_TEST_1;
import static de.hixdev.mailserverapp.data.TestData.CREATED_DATE_TEST_1;
import static de.hixdev.mailserverapp.data.TestData.MAIL_BODY_TEST_1;
import static de.hixdev.mailserverapp.data.TestData.MAIL_CC_TEST_1;
import static de.hixdev.mailserverapp.data.TestData.MAIL_FROM_TEST_1;
import static de.hixdev.mailserverapp.data.TestData.MAIL_TO_TEST_1;
import static de.hixdev.mailserverapp.data.TestData.MODIFIED_BY_TEST_1;
import static de.hixdev.mailserverapp.data.TestData.MODIFIED_DATE_TEST_1;
import static de.hixdev.mailserverapp.data.TestData.getEmailRecipientDtos;
import static de.hixdev.mailserverapp.entity.State.EMAIL_DRAFT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.hixdev.mailserverapp.data.TestData;
import de.hixdev.mailserverapp.dto.EmailDto;
import de.hixdev.mailserverapp.dto.EmailRecipientDto;
import de.hixdev.mailserverapp.entity.Email;
import de.hixdev.mailserverapp.entity.State;
import de.hixdev.mailserverapp.exception.EmailUpdateNotPossibleException;
import de.hixdev.mailserverapp.exception.ResourceNotFoundException;
import de.hixdev.mailserverapp.repository.EmailRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

  @Mock
  private EmailRepository emailRepository;

  @InjectMocks
  private EmailServiceImpl emailService;

  private EmailDto emailDto;
  private Email email;

  @BeforeEach
  public void setup() {
    email = Email.builder()
        .emailId(1L)
        .emailBody(MAIL_BODY_TEST_1)
        .emailFrom(MAIL_FROM_TEST_1)
        .emailTo(MAIL_TO_TEST_1)
        .emailCc(MAIL_CC_TEST_1)
        .state(EMAIL_DRAFT.getCode())
        .createdBy(CREATED_BY_TEST_1)
        .createdDate(CREATED_DATE_TEST_1)
        .lastModifiedBy(MODIFIED_BY_TEST_1)
        .lastModifiedDate(MODIFIED_DATE_TEST_1)
        .build();

    List<EmailRecipientDto> emailsTo = getEmailRecipientDtos(TestData.MAIL_TO_TEST_1);
    List<EmailRecipientDto> emailsCc = getEmailRecipientDtos(TestData.MAIL_CC_TEST_1);

    emailDto = EmailDto.builder()
        .emailId(1L)
        .emailBody(MAIL_BODY_TEST_1)
        .emailFrom(MAIL_FROM_TEST_1)
        .emailTo(emailsTo)
        .emailCc(emailsCc)
        .state(EMAIL_DRAFT.getCode())
        .createdBy(CREATED_BY_TEST_1)
        .createdDate(CREATED_DATE_TEST_1)
        .lastModifiedBy(MODIFIED_BY_TEST_1)
        .lastModifiedDate(MODIFIED_DATE_TEST_1)
        .build();
  }

  @DisplayName("Unit-Test for saveEmail")
  @Test
  void givenEmail_whenSaveEmail_thenReturnEmail() {
    // given
    email.setEmailId(null);
    given(emailRepository.save(any())).willReturn(email);

    // when
    EmailDto expectedEmail = emailService.saveEmail(emailDto);

    // then
    assertThat(expectedEmail).isNotNull();
  }

  @DisplayName("Unit-Test for fetchEmail with known id")
  @Test
  void givenEmailId_whenFetchEmail_thenReturnEmail() {
    // given
    Long emailId = 1L;
    given(emailRepository.findEmailByEmailId(emailId)).willReturn(java.util.Optional.of(email));

    // when
    EmailDto expectedEmail = emailService.fetchEmail(emailId);

    // then
    assertThat(expectedEmail).isNotNull();
  }

  @DisplayName("Unit-Test for fetchEmail with unknown id")
  @Test
  void givenEmailId_whenFetchEmail_thenShouldThrowResourceNotFoundException() {
    // given
    Long emailId = 3L;
    given(emailRepository.findEmailByEmailId(emailId)).willReturn(Optional.empty());

    //when
    org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      emailService.fetchEmail(emailId);
    });

  }


  @DisplayName("Unit-Test for fetchEmails")
  @Test
  void whenFetchEmails_thenReturnEmails() {
    // given
    List<Email> emails = new ArrayList<Email>();
    emails.add(email);
    given(emailRepository.findAll()).willReturn(emails);

    // when
    List<EmailDto> expectedEmails = emailService.fetchEmails();

    // then
    assertThat(expectedEmails).isNotNull();
  }

  @DisplayName("Unit-Test for updateEmail for Email with valid id")
  @Test
  void givenEmail_In_State_Draft_whenUpdateEmail_thenReturnUpdatedEmail() {
    // given
    email.setEmailBody("Changed Email Body");
    given(emailRepository.findEmailByEmailId(email.getEmailId())).willReturn(java.util.Optional.of(email));
    given(emailRepository.save(any())).willReturn(email);

    // when
    EmailDto expectedEmail = emailService.updateEmail(emailDto);

    // then
    assertThat(expectedEmail).isNotNull();
    assertThat(expectedEmail.getEmailBody()).isEqualTo(email.getEmailBody());
  }

  @DisplayName("Unit-Test for updateEmail for Email in State Draft with valid id")
  @Test
  void givenEmail_In_State_Draft_With_Invalid_Id_whenUpdateEmail_thenShouldThrowResourceNotFoundException() {
    // given
    email.setEmailBody("Changed Email Body");
    given(emailRepository.findEmailByEmailId(email.getEmailId())).willReturn(Optional.empty());

    // when
    org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      emailService.updateEmail(emailDto);
    });

  }

  @DisplayName("Unit-Test for updateEmail for Email in State SPam")
  @Test
  void givenEmail_In_State_Spam_whenUpdateEmail_thenShouldThrowhandleEmailUpdateNotPossibleException() {
    // given
    email.setEmailBody("Changed Email Body");
    email.setState(State.EMAIL_SPAM.getCode());
    emailDto.setState(State.EMAIL_SPAM.getCode());
    given(emailRepository.findEmailByEmailId(email.getEmailId())).willReturn(java.util.Optional.of(email));

    //when
    org.junit.jupiter.api.Assertions.assertThrows(EmailUpdateNotPossibleException.class, () -> {
      emailService.updateEmail(emailDto);
    });
  }

  @DisplayName("Unit-Test for deleteEmail with vaild id")
  @Test
  void givenValidEmailId_whenDeleteEmail_thenReturnNothing() {
    Long emailId = 1L;
    given(emailRepository.findEmailByEmailId(emailId)).willReturn(java.util.Optional.of(email));

    // when
    emailService.deleteEmail(emailId);

    //then
    verify(emailRepository, times(1)).deleteById(emailId);

  }

  @DisplayName("Unit-Test for deleteEmail with invaild id")
  @Test
  void givenInvalidEmailId_whenDeleteEmail_thenShouldThrowResourceNotFoundException() {
    // given
    Long emailId = 3L;
    given(emailRepository.findEmailByEmailId(emailId)).willReturn(Optional.empty());

    //when
    org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      emailService.deleteEmail(emailId);
    });
  }

  @DisplayName("Unit-Test for saveEmails")
  @Test
  void givenEmails_whenSaveEmails_thenReturnEmails() {
    // given
    List<EmailDto> emailsDto = new ArrayList<EmailDto>();
    emailsDto.add(emailDto);
    given(emailRepository.saveAll(any())).willReturn(List.of(email));

    // when
    List<EmailDto> expectedEmails = emailService.saveAllEmails(emailsDto);

    // then
    assertThat(expectedEmails).isNotNull();
  }

  @DisplayName("Unit-Test for updateEmails")
  @Test
  void givenEmails_whenUpdateEmails_thenReturnEmails() {
    // given
    List<EmailDto> emailsDto = new ArrayList<EmailDto>();
    emailsDto.add(emailDto);
    given(emailRepository.findEmailByEmailId(emailDto.getEmailId())).willReturn(
        java.util.Optional.of(email));
    given(emailRepository.saveAll(any())).willReturn(List.of(email));

    // when
    List<EmailDto> expectedEmails = emailService.updateAllEmails(emailsDto);

    // then
    assertThat(expectedEmails).isNotNull();
  }

  @DisplayName("Unit-Test for deleteEmails with valid ids")
  @Test
  void givenEmails_With_Valid_ids_whenDeleteEmails_thenReturnNothing() {
    // given
    List<EmailDto> emailsDto = new ArrayList<EmailDto>();
    emailsDto.add(emailDto);
    given(emailRepository.findEmailByEmailId(any())).willReturn(java.util.Optional.of(email));

    // when
    emailService.deleteAllEmails(emailsDto);

    //then
    verify(emailRepository, times(1)).deleteAll(any());
  }

  @DisplayName("Unit-Test for deleteEmails with invalid id")
  @Test
  void givenEmails_With_Invalid_id_whenDeleteEmails_thenShouldThrowResourceNotFoundException() {
    // given
    List<EmailDto> emailsDto = new ArrayList<EmailDto>();
    emailsDto.add(emailDto);
    given(emailRepository.findEmailByEmailId(any())).willReturn(Optional.empty());

    //when
    org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      emailService.deleteAllEmails(emailsDto);
    });
  }
}
