package com.demidrolll.pet.bank.domain.client.repository;

import com.demidrolll.pet.bank.domain.client.repository.model.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

}
