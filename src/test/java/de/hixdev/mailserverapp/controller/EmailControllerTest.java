package de.hixdev.mailserverapp.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hixdev.mailserverapp.dto.EmailDto;
import de.hixdev.mailserverapp.dto.EmailRecipientDto;
import de.hixdev.mailserverapp.dto.EmailsDto;
import de.hixdev.mailserverapp.entity.State;
import de.hixdev.mailserverapp.service.EmailService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
@MockBean(JpaMetamodelMappingContext.class)
class EmailControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmailService emailService;

  private EmailDto emailDto;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    List<EmailRecipientDto> emailsTo = new ArrayList<EmailRecipientDto>();
    emailsTo.add(new EmailRecipientDto("jack.river@hixdev.de"));
    List<EmailRecipientDto> emailsCc = new ArrayList<EmailRecipientDto>();
    emailsTo.add(new EmailRecipientDto("eric.lars@hixdev.de"));

    emailDto = EmailDto.builder()
        .emailBody("Hello")
        .emailFrom("hix@hixdev.de")
        .emailTo(emailsTo)
        .emailCc(emailsCc)
        .state(State.EMAIL_DRAFT.getCode())
        .createdBy("HIX")
        .createdDate(null)
        .lastModifiedBy("HIX")
        .lastModifiedDate(null)
        .build();
  }

  @Test
  void givenEmail_when_Create_thenReturnSavedEmail() throws Exception {
    //given
    BDDMockito.given(emailService.saveEmail(any()))
        .willAnswer((invocation) -> invocation.getArgument(0));

    // when
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders
        .post("/api/v1/emails/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emailDto)));

    // then
    response.andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.emailFrom",
            is(emailDto.getEmailFrom())))
        .andExpect(jsonPath("$.state",
            is(emailDto.getState())));

  }

  @Test
  void givenEmail_when_Get_thenReturnEmail() throws Exception {
    //given
    emailDto.setEmailId(1L);
    BDDMockito.given(emailService.fetchEmail(emailDto.getEmailId())).willReturn(emailDto);

    // when
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders
        .get("/api/v1/emails/get/{id}", emailDto.getEmailId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emailDto)));

    // then
    response.andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.emailFrom",
            is(emailDto.getEmailFrom())))
        .andExpect(jsonPath("$.state",
            is(emailDto.getState())));
  }

  @Test
  void when_GetAll_thenReturnEmails() throws Exception {
    //given
    emailDto.setEmailId(1L);
    List<EmailDto> emailDtos = new ArrayList<EmailDto>();
    emailDtos.add(emailDto);
    BDDMockito.given(emailService.fetchEmails()).willReturn(emailDtos);

    // when
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders
        .get("/api/v1/emails/getAll")
        .content(objectMapper.writeValueAsString(emailDtos))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emailDto)));

    // then
    response.andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()",
            is(emailDtos.size())));
  }

  @Test
  void givenEmail_when_Update_thenReturnUpdatedEmail() throws Exception {
    //given
    emailDto.setEmailId(1L);
    emailDto.setEmailBody("Hello World");
    BDDMockito.given(emailService.updateEmail(any())).willReturn(emailDto);

    // when
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders
        .put("/api/v1/emails/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emailDto)));

    // then
    response.andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void givenEmail_Id_when_Delete_thenReturn_HTTP_STATUS_200() throws Exception {
    //given
    emailDto.setEmailId(1L);
    emailDto.setEmailBody("Hello World");
    BDDMockito.given(emailService.deleteEmail(any())).willReturn(true);

    // when
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders
        .delete("/api/v1/emails/delete/{id}", emailDto.getEmailId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emailDto)));

    // then
    response.andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void givenEmails_whenCreateAll_thenReturnSavedEmail() throws Exception {
    //given
    emailDto.setEmailId(1L);
    List<EmailDto> emailDtos = new ArrayList<EmailDto>();
    emailDtos.add(emailDto);
    EmailsDto emailsDto = new EmailsDto(emailDtos);
    BDDMockito.given(emailService.saveAllEmails(any()))
        .willAnswer((invocation) -> invocation.getArgument(0));

    // when
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders
        .post("/api/v1/emails/createAll")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emailsDto)));

    // then
    response.andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  void givenEmails_whenUpdateAll_thenReturnpdatedEmails() throws Exception {
    //given
    emailDto.setEmailId(1L);
    List<EmailDto> emailDtos = new ArrayList<EmailDto>();
    emailDtos.add(emailDto);
    EmailsDto emailsDto = new EmailsDto(emailDtos);
    BDDMockito.given(emailService.updateAllEmails(any()))
        .willAnswer((invocation) -> invocation.getArgument(0));

    // when
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders
        .put("/api/v1/emails/updateAll")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emailsDto)));

    // then
    response.andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void givenEmails_whenDeleteAll_thenReturn_HTTP_STATUS_200() throws Exception {
    //given
    emailDto.setEmailId(1L);
    List<EmailDto> emailDtos = new ArrayList<EmailDto>();
    emailDtos.add(emailDto);
    EmailsDto emailsDto = new EmailsDto(emailDtos);
    BDDMockito.given(emailService.deleteAllEmails(any())).willReturn(true);

    // when
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders
        .delete("/api/v1/emails/deleteAll")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emailsDto)));

    // then
    response.andDo(print())
        .andExpect(status().isOk());
  }
}
