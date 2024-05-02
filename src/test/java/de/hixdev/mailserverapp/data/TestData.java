package de.hixdev.mailserverapp.data;

import de.hixdev.mailserverapp.dto.EmailDto;
import de.hixdev.mailserverapp.dto.EmailRecipientDto;
import de.hixdev.mailserverapp.entity.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestData {


  public static final String MAIL_BODY_TEST_1 = "Hello";
  public static final String MAIL_FROM_TEST_1 = "hix@hixdev.de";
  public static final String MAIL_TO_TEST_1 = "jack.river@hixdev.de";
  public static final String MAIL_CC_TEST_1 = "eric.lars@hixdev.de";
  public static final String EMAIL_STATE_DRAFT = "0";
  public static final String CREATED_BY_TEST_1 = "HIX";
  public static final LocalDateTime CREATED_DATE_TEST_1 = LocalDateTime.now();
  public static final String MODIFIED_BY_TEST_1 = "HIX";
  public static final LocalDateTime MODIFIED_DATE_TEST_1 = LocalDateTime.now().plusHours(1);


  public static Email buildEmailTestDataObject(Long emailId) {
    return Email.builder()
        .emailBody(MAIL_BODY_TEST_1)
        .emailFrom(MAIL_FROM_TEST_1)
        .emailTo(MAIL_TO_TEST_1)
        .emailCc(MAIL_CC_TEST_1)
        .state(EMAIL_STATE_DRAFT)
        .build();
  }

  public static EmailDto buildEmailDtoTestDataObject(Long emailId) {
    final List<EmailRecipientDto> emailsTo = getEmailRecipientDtos(MAIL_TO_TEST_1);
    final List<EmailRecipientDto> emailsCc = getEmailRecipientDtos(MAIL_CC_TEST_1);

    return EmailDto.builder()
        .emailBody(MAIL_BODY_TEST_1)
        .emailFrom(MAIL_FROM_TEST_1)
        .emailTo(emailsTo)
        .emailCc(emailsCc)
        .state(EMAIL_STATE_DRAFT)
        .createdBy(CREATED_BY_TEST_1)
        .createdDate(CREATED_DATE_TEST_1)
        .lastModifiedBy(MODIFIED_BY_TEST_1)
        .lastModifiedDate(MODIFIED_DATE_TEST_1)
        .build();
  }

  public static List<EmailRecipientDto> getEmailRecipientDtos(String email) {
    List<EmailRecipientDto> emailsTo = new ArrayList<EmailRecipientDto>();
    emailsTo.add(new EmailRecipientDto(email));
    return emailsTo;
  }
}
