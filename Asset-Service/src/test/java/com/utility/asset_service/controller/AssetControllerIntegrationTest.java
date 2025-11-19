package com.utility.asset_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utility.asset_service.dto.req.AssetReqDto;
import com.utility.asset_service.model.AssetStatus;
import com.utility.asset_service.model.AssetType;
import com.utility.asset_service.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AssetControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private AssetReqDto reqDto;

    @BeforeEach
    void setUp() {
        reqDto = AssetReqDto.builder()
                .name("Integration Asset")
                .assetType(AssetType.POLE)
                .city("Pune")
                .state(State.MH)
                .latitude(18.5)
                .longitude(73.8)
                .status(AssetStatus.ACTIVE)
                .build();
    }

    @Test
    void createAndFetchAsset() throws Exception {
        // Create asset
        MvcResult createResult = mockMvc.perform(post("/api/v1/assets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqDto)))
                .andExpect(status().isOk())
                .andReturn();
        String response = createResult.getResponse().getContentAsString();
        assertThat(response).contains("Integration Asset");

        // Fetch all assets
        mockMvc.perform(get("/api/v1/assets"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Integration Asset")));
    }
    
    @Test
	void fetchAssetById() throws Exception {

		MvcResult createResult = mockMvc.perform(post("/api/v1/assets").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reqDto))).andExpect(status().isOk()).andReturn();

		String response = createResult.getResponse().getContentAsString();
		String assetId = objectMapper.readTree(response).get("assetId").asText();
		
		mockMvc.perform(get("/api/v1/assets/"+assetId))
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.containsString(assetId)));

	}
    
    @Test
    void fetchNonExistentAssetById() throws Exception{
    	
    	mockMvc.perform(get("/api/v1/assets/NON-EXISTENT"))
    			.andExpect(status().isNotFound());
    	
    }

    @Test
    void updateAndDeleteAsset() throws Exception {
        // Create asset
        MvcResult createResult = mockMvc.perform(post("/api/v1/assets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqDto)))
                .andExpect(status().isOk())
                .andReturn();
        String response = createResult.getResponse().getContentAsString();
        String assetId = objectMapper.readTree(response).get("assetId").asText();

        // Update asset
        reqDto.setName("Updated Integration Asset");
        mockMvc.perform(put("/api/v1/assets/" + assetId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Updated Integration Asset")));

        // Delete asset
        mockMvc.perform(delete("/api/v1/assets/" + assetId))
                .andExpect(status().isNoContent());
    }
}