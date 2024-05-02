package de.hixdev.mailserverapp.repository;

import static de.hixdev.mailserverapp.constants.Constants.SPAM_EMAIL;
import static de.hixdev.mailserverapp.entity.State.EMAIL_SPAM;
import static org.assertj.core.api.Assertions.assertThat;

import de.hixdev.mailserverapp.data.TestData;
import de.hixdev.mailserverapp.entity.Email;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class EmailRepositoryTest {


  @Autowired
  private EmailRepository emailRepository;

  private Email email;

  @BeforeEach
  public void setup() {
    email = TestData.buildEmailTestDataObject(null);
  }

  @DisplayName("Unit-Test for load an Email By Id")
  @Test
  void givenEmailId_whenGetEmailByEmailId_thenReturnEmailObject() {
    //given via setup - beforeeach
    emailRepository.save(email);

    // when
    Email emailPersisted = emailRepository.findEmailByEmailId(email.getEmailId()).get();

    // then - verify the output
    assertThat(emailPersisted).isNotNull();
  }

  @DisplayName("Unit-Test for Find Email By EmailFrom And State Is Not")
  @Test
  void givenEmailFrom_State_whenFindEmailByEmailFromAndStateIsNot_thenReturnEmailList() {
    //given via setup - beforeeach
    email.setEmailFrom(SPAM_EMAIL);
    emailRepository.save(email);

    // when
    List<Email> emailListOfSpamCandidates = emailRepository.findEmailByEmailFromAndStateIsNot(SPAM_EMAIL, EMAIL_SPAM.getCode());

    // then - verify the output
    assertThat(emailListOfSpamCandidates).isNotNull();
  }


  @DisplayName("Unit-Test for saveEmail with invalid data - emailfrom empty")
  @Test
  void givenEmail_EmailFrom_empty_whenSaveEmail_thenReturnThrowException() {
    // given
    email.setEmailId(null);
    email.setEmailFrom(null);

    // when
    org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class, () -> {
      emailRepository.save(email);
    });
  }
}
