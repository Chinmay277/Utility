package com.utility.workforce_service.configs;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.utility.workforce_service.entity.Employee;
import com.utility.workforce_service.req.dto.EmployeeReqDto;

@Configuration
public class ModelMapperConfiguration {

		@Bean
		public ModelMapper modelMapper() {
			return new ModelMapper();
			
		}
		
		@Bean
		@Qualifier("empModelMapper")
		public ModelMapper empModelMapper() {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.addMappings(new PropertyMap<EmployeeReqDto, Employee>() {

				@Override
				protected void configure() {
					// TODO Auto-generated method stub
					skip(destination.getRoles());
				}
			});
			return modelMapper;
		}
}
