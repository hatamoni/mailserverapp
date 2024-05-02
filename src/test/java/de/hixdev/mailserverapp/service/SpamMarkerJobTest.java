package de.hixdev.mailserverapp.service;

import static de.hixdev.mailserverapp.constants.Constants.SPAM_EMAIL;
import static de.hixdev.mailserverapp.entity.State.EMAIL_SPAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import de.hixdev.mailserverapp.data.TestData;
import de.hixdev.mailserverapp.entity.Email;
import de.hixdev.mailserverapp.repository.EmailRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpamMarkerJobTest {

  @Mock
  private EmailRepository emailRepository;

  @InjectMocks
  private SpamMarkerJob spamMarkerJob;

  @Test
  void givenSpamMailsCandidates__whenSpamMarkerJobRuns_thenShouldReturnExpectedResultList()
      throws ExecutionException, InterruptedException {
    //given
    Email email1 = TestData.buildEmailTestDataObject(1L);
    email1.setEmailFrom(SPAM_EMAIL);
    Email email2 = TestData.buildEmailTestDataObject(2L);
    email2.setEmailFrom(SPAM_EMAIL);
    List<Email> emails = new ArrayList<Email>();
    emails.add(email1);
    emails.add(email2);

    given(emailRepository.findEmailByEmailFromAndStateIsNot(SPAM_EMAIL, EMAIL_SPAM.getCode())).willReturn(emails);

    //when
    Future<Integer> spamMails = spamMarkerJob.recognizeSpamMailsAndMarkThemAsSpam();

    //then
    assertEquals(2, spamMails.get().intValue());

  }

  @Test
  void givenNoneSpamMailsCandidates__whenSpamMarkerJobRuns_thenShouldReturnExpectedResultList()
      throws ExecutionException, InterruptedException {
    //given
    Email email1 = TestData.buildEmailTestDataObject(1L);
    email1.setEmailFrom(TestData.MAIL_FROM_TEST_1);
    Email email2 = TestData.buildEmailTestDataObject(2L);
    email2.setEmailFrom(TestData.MAIL_FROM_TEST_1);
    List<Email> emails = new ArrayList<Email>();
    emails.add(email1);
    emails.add(email2);

    given(emailRepository.findEmailByEmailFromAndStateIsNot(SPAM_EMAIL, EMAIL_SPAM.getCode())).willReturn(emails);

    //when
    Future<Integer> spamMails = spamMarkerJob.recognizeSpamMailsAndMarkThemAsSpam();

    //then
    assertEquals(0, spamMails.get().intValue());

  }

}
