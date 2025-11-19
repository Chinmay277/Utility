package com.utility.workorder_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.utility.workorder_service.enums.WoPriority;
import com.utility.workorder_service.enums.WoStatus;
import com.utility.workorder_service.enums.WoType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "work-orders")
@Data
@Getter
@Setter
public class WorkOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String woId;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private WoType type;
	private String description;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private WoStatus status;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private WoPriority priority;
	private LocalDateTime createdDate;
	private LocalDateTime dueDate;
	private String assetId;
	private String assignedTo;
}
