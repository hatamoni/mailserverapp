package de.hixdev.mailserverapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Schema(
    name = "ErrorResponse",
    description = "Schema the error response dto"
)
public class ErrorResponseDto {

  @Schema(
      description = "API path"
  )
  private String apiPath;

  @Schema(
      description = "Error code of the occurred error"
  )
  private HttpStatus errorCode;

  @Schema(
      description = "Error message describing the error occurred"
  )
  private String errorMessage;

  @Schema(
      description = "Date and time when the error occurred"
  )
  private LocalDateTime errorDateTime;

}
