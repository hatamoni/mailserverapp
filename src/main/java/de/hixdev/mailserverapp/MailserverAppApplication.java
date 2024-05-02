package de.hixdev.mailserverapp;

import de.hixdev.mailserverapp.audit.MsApiAuditImpl;
import de.hixdev.mailserverapp.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Due to Unit Test of EmailRepository, this class is not annotated with @EnableJpaAuditing(...)
// instead it is annotated with @EnableJpaAuditing and use the default @Bean
//@EnableJpaAuditing(auditorAwareRef = "msApiAuditImpl")
@EnableJpaAuditing
@SpringBootApplication
public class MailserverAppApplication {

  private static final Logger LOG = LoggerFactory.getLogger(MailserverAppApplication.class);

  @Bean
  protected MsApiAuditImpl msApiAuditImpl() {
    return new MsApiAuditImpl();
  }

  public static void main(String[] args) {
    LOG.info(Constants.LOG_MESSAGE_START, "MailserverAppApplication", "main");

    SpringApplication.run(MailserverAppApplication.class, args);

    LOG.info(Constants.LOG_MESSAGE_END, "MailserverAppApplication", "main");

  }

}
