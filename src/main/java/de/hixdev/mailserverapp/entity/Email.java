package de.hixdev.mailserverapp.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "email")
public class Email {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long emailId;

  @NotNull
  @Column(name = "email_from", nullable = false)
  private String emailFrom;

  @NotNull
  @Column(name = "email_to", nullable = false)
  private String emailTo;

  @Column(name = "email_cc", nullable = true)
  private String emailCc;

  @NotNull
  @Column(name = "email_body", nullable = false)
  private String emailBody;

  @NotNull
  @Column(name = "state", nullable = false)
  private String state;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  private LocalDateTime createdDate;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  private String createdBy;

  @LastModifiedDate
  @Column(name = "last_modified_date", updatable = false)
  private LocalDateTime lastModifiedDate;

  @LastModifiedBy
  @Column(name = "last_modified_by", updatable = false)
  private String lastModifiedBy;

}
