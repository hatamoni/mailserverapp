package de.hixdev.mailserverapp.audit;


import de.hixdev.mailserverapp.constants.Constants;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

// Due to Unit Test of EmailRepository, this class is not annotated with @Component
//@Component("msApiAuditImpl")
public class MsApiAuditImpl implements AuditorAware<String> {

  /**
   * Returns the current auditor-username of the application.
   *
   * @return the current auditor-username.
   */
  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of(Constants.MS_API_USER);
  }

}
