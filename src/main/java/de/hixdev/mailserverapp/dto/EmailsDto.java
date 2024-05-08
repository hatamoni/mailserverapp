package de.hixdev.mailserverapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
    description = "Emails Dto Model Information"
)

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailsDto {

  //BugFix of bulk email sending
  @Valid
  private List<EmailDto> emails;

}
