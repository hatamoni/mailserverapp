package de.hixdev.mailserverapp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import de.hixdev.mailserverapp.entity.Email;
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
    email = Email.builder()
        .emailBody("Hello")
        .emailFrom("hix@hixdev.de")
        .emailTo("jack.river@hixdev.de")
        .emailCc("eric.lars@hixdev.de")
        .state("0")
        .build();
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

}
