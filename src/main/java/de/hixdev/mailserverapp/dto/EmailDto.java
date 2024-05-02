package de.hixdev.mailserverapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Schema(
    description = "EmailDto Model Information"
)

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {

  private Long emailId;

  @Schema(
      description = "Sender Email Address"
  )
  @NotEmpty(message = "EmailFrom (Sender-Email) should not be null or empty")
  @Email(message = "Email address should be valid")
  private String emailFrom;

  @Schema(
      description = "Recipient(s) Email Address(es)"
  )
  @EmailToList
  private List<EmailRecipientDto> emailTo;

  @Schema(
      description = "Carbon Copy Email Address(es) (Optional). If not provided, it will be empty. If provided, it should contains at leat one valid email address"
  )
  @EmailCcList
  private List<EmailRecipientDto> emailCc;

  @Schema(
      description = "Email Body"
  )
  private String emailBody;

  @Schema(
      description = "Email State. It should be one of the following values: '0=DRAFT','1=SENT', '2=DELETED', '3=SPAM'"
  )
  private String state;

  @Schema(
      description = "Date when the  Email was created . It should be in the format of 'yyyy-MM-dd HH:mm:ss'"
  )
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Berlin")
  private LocalDateTime createdDate;

  @Schema(
      description = "User who created the  Email"
  )
  private String createdBy;

  @Schema(
      description = "Date when the  Email was last modified . It should be in the format of 'yyyy-MM-dd HH:mm:ss'"
  )
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Berlin")
  private LocalDateTime lastModifiedDate;

  @Schema(
      description = "User who last modified the Email"
  )
  private String lastModifiedBy;

}
