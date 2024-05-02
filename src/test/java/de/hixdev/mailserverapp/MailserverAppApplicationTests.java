package de.hixdev.mailserverapp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class MailserverAppApplicationTests {

  @Test
  void contextLoads(ApplicationContext context) {
    assertThat(context).isNotNull();
  }

}
