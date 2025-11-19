package com.utility.workforce_service.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utility.workforce_service.entity.Capability;

@Repository
public interface CapabilityRepository extends JpaRepository<Capability, Long> {

}
