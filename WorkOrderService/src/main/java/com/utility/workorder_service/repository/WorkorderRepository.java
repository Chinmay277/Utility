package com.utility.workorder_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utility.workorder_service.entity.WorkOrder;

@Repository
public interface WorkorderRepository extends JpaRepository<WorkOrder, Long> {
	

}
