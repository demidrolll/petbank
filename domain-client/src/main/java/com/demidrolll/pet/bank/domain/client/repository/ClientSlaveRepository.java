package com.demidrolll.pet.bank.domain.client.repository;

import com.demidrolll.pet.bank.domain.client.config.SlaveRepository;
import com.demidrolll.pet.bank.domain.client.repository.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SlaveRepository
public interface ClientSlaveRepository extends JpaRepository<Client, Long> {

}
