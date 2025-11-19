package com.utility.asset_service.svc;

import com.utility.asset_service.dto.req.AssetReqDto;
import com.utility.asset_service.dto.resp.AssetRespDto;
import com.utility.asset_service.entity.Asset;
import com.utility.asset_service.model.AssetStatus;
import com.utility.asset_service.model.AssetType;
import com.utility.asset_service.model.State;
import com.utility.asset_service.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {
    @Mock
    private AssetRepository assetRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AssetService assetService;

    private AssetReqDto reqDto;
    private Asset asset;
    private AssetRespDto respDto;

    @BeforeEach
    void setUp() {
        reqDto = AssetReqDto.builder()
                .name("Test Asset")
                .assetType(AssetType.POLE)
                .city("Test City")
                .state(State.MH)
                .latitude(1.0)
                .longitude(2.0)
                .status(AssetStatus.ACTIVE)
                .parentAssetId(null)
                .parentId(null)
                .build();
        asset = Asset.builder()
                .id(1L)
                .assetId("POL-0001")
                .name("Test Asset")
                .assetType(AssetType.POLE)
                .city("Test City")
                .state(State.MH)
                .latitude(1.0)
                .longitude(2.0)
                .status(AssetStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        respDto = new AssetRespDto();
        respDto.setAssetId("POL-0001");
        respDto.setName("Test Asset");
    }

    @Test
    void createAsset_shouldReturnOk_whenAssetCreated() {
        when(modelMapper.map(any(AssetReqDto.class), eq(Asset.class))).thenReturn(asset);
        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class))).thenReturn(1L);
        when(assetRepository.save(any(Asset.class))).thenReturn(asset);
        when(modelMapper.map(any(Asset.class), eq(AssetRespDto.class))).thenReturn(respDto);

        ResponseEntity<AssetRespDto> response = assetService.createAsset(reqDto);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAssetId()).isEqualTo("POL-0001");
    }

    @Test
    void getAllAssets_shouldReturnList() {
        when(assetRepository.findAll()).thenReturn(Collections.singletonList(asset));
        when(modelMapper.map(any(Asset.class), eq(AssetRespDto.class))).thenReturn(respDto);
        ResponseEntity<List<AssetRespDto>> response = assetService.getAllAssets();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void getAssetById_shouldReturnAsset_whenFound() {
        when(assetRepository.findByAssetId("POL-0001")).thenReturn(asset);
        when(modelMapper.map(any(Asset.class), eq(AssetRespDto.class))).thenReturn(respDto);
        ResponseEntity<AssetRespDto> response = assetService.getAssetById("POL-0001");
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getAssetById_shouldReturnNotFound_whenNotFound() {
        when(assetRepository.findByAssetId("NOT-EXIST")).thenReturn(null);
        ResponseEntity<AssetRespDto> response = assetService.getAssetById("NOT-EXIST");
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    void updateAsset_shouldUpdateFields() {
        when(assetRepository.findByAssetId("POL-0001")).thenReturn(asset);
        when(assetRepository.save(any(Asset.class))).thenReturn(asset);
        when(modelMapper.map(any(Asset.class), eq(AssetRespDto.class))).thenReturn(respDto);
        reqDto.setName("Updated Name");
        ResponseEntity<AssetRespDto> response = assetService.updateAsset("POL-0001", reqDto);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void updateAsset_shouldReturnNotFound_whenNotFound() {
        when(assetRepository.findByAssetId("NOT-EXIST")).thenReturn(null);
        ResponseEntity<AssetRespDto> response = assetService.updateAsset("NOT-EXIST", reqDto);
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    void deleteAsset_shouldDelete_whenFound() {
        when(assetRepository.findByAssetId("POL-0001")).thenReturn(asset);
        doNothing().when(assetRepository).delete(asset);
        ResponseEntity<Void> response = assetService.deleteAsset("POL-0001");
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void deleteAsset_shouldReturnNotFound_whenNotFound() {
        when(assetRepository.findByAssetId("NOT-EXIST")).thenReturn(null);
        ResponseEntity<Void> response = assetService.deleteAsset("NOT-EXIST");
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}
