package de.hixdev.mailserverapp.repository;


import de.hixdev.mailserverapp.entity.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

  Optional<Email> findEmailByEmailId(Long emailId);

}
