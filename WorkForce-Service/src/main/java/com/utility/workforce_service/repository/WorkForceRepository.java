package com.utility.workforce_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.utility.workforce_service.entity.Employee;

@Repository
public interface WorkForceRepository extends JpaRepository<Employee, Long> {

	Boolean existsByName(String role);

}
