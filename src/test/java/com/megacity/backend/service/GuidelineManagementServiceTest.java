package com.megacity.backend.service;

import com.megacity.backend.domain.entity.Guideline;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.guideline_management.service.impl.GuidelineServiceImpl;
import com.megacity.backend.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class GuidelineManagementServiceTest {

    private final ResponseUtil responseUtils = mock(ResponseUtil.class);
    private final JdbcTemplate writeJdbcTemplate = mock(JdbcTemplate.class);
    private final JdbcTemplate readJdbcTemplate = mock(JdbcTemplate.class);

    private final GuidelineServiceImpl guidelineService = new GuidelineServiceImpl(
            writeJdbcTemplate, readJdbcTemplate, responseUtils);

    private Guideline createTestGuideline() {
        return Guideline.builder()
                .guidanceId(1)
                .title("Test Guideline")
                .description("Test Description")
                .category("Test Category")
                .priority("High")
                .relatedTo("Test Related")
                .build();
    }

    @Test
    @DisplayName("Create guideline success scenario")
    void createGuideline() {
        Guideline guideline = createTestGuideline();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = guidelineService.addNewGuideline(guideline);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Create guideline error scenario")
    void createGuidelineError() {
        Guideline guideline = createTestGuideline();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = guidelineService.addNewGuideline(guideline);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Update guideline success scenario")
    void updateGuideline() {
        Guideline guideline = createTestGuideline();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = guidelineService.updateGuideline(guideline);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Update guideline error scenario")
    void updateGuidelineError() {
        Guideline guideline = createTestGuideline();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = guidelineService.updateGuideline(guideline);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Delete guideline success scenario")
    void deleteGuideline() {
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = guidelineService.deleteGuideline(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Delete guideline error scenario")
    void deleteGuidelineError() {
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = guidelineService.deleteGuideline(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Fetch guideline by ID success scenario")
    void fetchGuidelineById() {
        Guideline guideline = createTestGuideline();
        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(guideline);
        when(responseUtils.wrapSuccess(any(Guideline.class), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = guidelineService.fetchGuidelineById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Fetch guideline by ID error scenario")
    void fetchGuidelineByIdError() {
        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = guidelineService.fetchGuidelineById(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Fetch all guidelines success scenario")
    void fetchAllGuidelines() {
        List<Guideline> guidelines = Collections.singletonList(createTestGuideline());
        when(writeJdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(guidelines);
        when(responseUtils.wrapSuccess(anyList(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = guidelineService.fetchAllGuidelineRecords(0, 10);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Fetch all guidelines error scenario")
    void fetchAllGuidelinesError() {
        when(writeJdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = guidelineService.fetchAllGuidelineRecords(0, 10);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
