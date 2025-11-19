package com.utility.asset_service.dto.resp;

import java.util.List;
import java.util.Map;

import com.utility.asset_service.entity.Asset;

import lombok.Data;

@Data
public class AssetByTypeRespDto {
	
	Map<String, List<Asset>> assetByTypesSorted;

}
