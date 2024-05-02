package de.hixdev.mailserverapp.repository;

import de.hixdev.mailserverapp.entity.Email;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

  public interface MailsPagingAndSortingRepository extends PagingAndSortingRepository<Email, Long> {

    Slice<Email> findEmailByState(int state, Pageable request);

  }
