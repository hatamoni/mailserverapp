package de.hixdev.mailserverapp.service;

import static de.hixdev.mailserverapp.constants.Constants.LOG_MESSAGE_END;
import static de.hixdev.mailserverapp.constants.Constants.LOG_MESSAGE_START;

import de.hixdev.mailserverapp.constants.Constants;
import de.hixdev.mailserverapp.entity.Email;
import de.hixdev.mailserverapp.entity.State;
import de.hixdev.mailserverapp.repository.EmailRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class SpamMarkerJob {

  private static final Logger LOG = LoggerFactory.getLogger(SpamMarkerJob.class);

  @Autowired
  private EmailRepository emailRepository;

  @Async
  @Scheduled(cron = "${cron-spammarkerjob}")
  @Scheduled
  public Future<Integer> recognizeSpamMailsAndMarkThemAsSpam()  {
    LOG.info(LOG_MESSAGE_START , "Starting SpamMarkerJob for recognizing spam E-Mails.", "");

    List<Email> emails = emailRepository.findEmailByEmailFromAndStateIsNot(Constants.SPAM_EMAIL, State.EMAIL_SPAM.getCode());
    List<Email> spamEmails = new ArrayList<>();
    for (Email email : emails) {
      if(email.getEmailFrom().equalsIgnoreCase(Constants.SPAM_EMAIL)){
        email.setState(State.EMAIL_SPAM.getCode());
        emailRepository.save(email);
        spamEmails.add(email);
      }
    }

    LOG.info(LOG_MESSAGE_END ,"Recognized E-Mails as Spam and marked them as such ", spamEmails.size());
    return new AsyncResult<>(spamEmails.size());
  }

}
