package com.utility.workforce_service.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.relation.RoleInfoNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.utility.workforce_service.controller.WorkForceController;
import com.utility.workforce_service.entity.Employee;
import com.utility.workforce_service.entity.Role;
import com.utility.workforce_service.exceptions.RoleNotFoundException;
import com.utility.workforce_service.repository.WorkForceRepository;
import com.utility.workforce_service.req.dto.EmployeeReqDto;
import com.utility.workforce_service.util.EmployeeUtil;

@Service
public class WorkForceService {

	@Autowired
    private WorkForceRepository repository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	@Qualifier("empModelMapper")
	ModelMapper modelMapper;
	
	@Autowired
	EmployeeUtil employeeUtil;

	public ResponseEntity<?> addEmployee(EmployeeReqDto empReqDto) {
		// TODO Auto-generated method stubworkForceController
		
		Role role;
		
		Set<Role> roles = new HashSet<>();
		
		roles = empReqDto.getRoles().stream().peek(r -> {
			if(!roleService.ifRoleExists(r)) {
				throw new RoleNotFoundException("ROLE "+r+" DOES NOT EXISTS");
			}
		}).map(r -> roleService.getByRoleName(r))
		.collect(Collectors.toSet());
		
		Employee employee = modelMapper.map(empReqDto, Employee.class);
		
		employee.setRoles(roles);
		employee.setEmployeeId(employeeUtil.createEmpId());
		System.out.println(employee.toString());
		repository.save(employee);
		
		return ResponseEntity.ok().body("Employee Created Successfully!");
	}


}
