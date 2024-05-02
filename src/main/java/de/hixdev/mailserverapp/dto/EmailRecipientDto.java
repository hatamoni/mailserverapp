package de.hixdev.mailserverapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
    description = "EmailRecipientDto Model Information"
)

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRecipientDto {

  @Schema(
      description = "Recipient-Email-Address(es) (Optional). If not provided, it will be empty. If provided, it should contains at least one valid email address or more adresses seperated by ';'."
  )
  @Email(message = "Email address should be valid")
  private String email;

}
