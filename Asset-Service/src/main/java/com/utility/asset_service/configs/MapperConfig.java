package com.utility.asset_service.configs;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.utility.asset_service.dto.req.AssetReqDto;
import com.utility.asset_service.entity.Asset;

@Configuration
public class MapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
	    ModelMapper modelMapper = new ModelMapper();
	    modelMapper.addMappings(new PropertyMap<AssetReqDto, Asset>() {
	        @Override
	        protected void configure() {
	            skip(destination.getId());
	            skip(destination.getParent());
	        }
	    });
	    return modelMapper;
	}

}