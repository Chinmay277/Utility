package com.utility.asset_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utility.asset_service.dto.req.AssetReqDto;
import com.utility.asset_service.dto.resp.AssetRespDto;
import com.utility.asset_service.svc.AssetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("api/v1/assets")
public class AssetController {

	@Autowired
	AssetService assetService;
	
	@PostMapping(produces = "application/json")
	public ResponseEntity<AssetRespDto> postMethodName(@RequestBody AssetReqDto reqDto) {
		//TODO: process POST request
		
		return assetService.createAsset(reqDto);
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> getAllAssets() {
		return assetService.getAllAssets();
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> getAssetById(@PathVariable("id") String assetId) {
		return assetService.getAssetById(assetId);
	}

	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> updateAsset(@PathVariable("id") String assetId, @RequestBody AssetReqDto reqDto) {
		return assetService.updateAsset(assetId, reqDto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteAsset(@PathVariable("id") String assetId) {
		return assetService.deleteAsset(assetId);
	}
	
	@GetMapping(value = "/group-by-type")
	public ResponseEntity<?> getAssetGroupByType(){
		return assetService.getAssetGroupbyType();
	}
	
	@PostMapping(value = "/ifAssetExists/{assetId}", produces = "application/json")
	public ResponseEntity<Boolean> ifAssetExists(@PathVariable("assetId") String assetId) {
		return assetService.ifAssetExists(assetId);
	}
	
	
	
	
}