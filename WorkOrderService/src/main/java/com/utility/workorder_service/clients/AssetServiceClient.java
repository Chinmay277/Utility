package com.utility.workorder_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name ="ASSET-SERVICE")
public interface AssetServiceClient {
	
	@PostMapping("api/v1/assets//ifAssetExists/{assetId}")
	ResponseEntity<Boolean> ifAssetExists(@PathVariable("assetId") String assetId); 

}
