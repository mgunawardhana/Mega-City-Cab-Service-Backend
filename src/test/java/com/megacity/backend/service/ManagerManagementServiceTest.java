package com.megacity.backend.service;

import com.megacity.backend.domain.entity.Manager;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.manager_management.service.impl.ManagerServiceImpl;
import com.megacity.backend.util.ResponseUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class ManagerManagementServiceTest {

    private final ResponseUtil responseUtils = mock(ResponseUtil.class);
    private final JdbcTemplate writeJdbcTemplate = mock(JdbcTemplate.class);
    private final JdbcTemplate readJdbcTemplate = mock(JdbcTemplate.class);

    private final ManagerServiceImpl managerService = new ManagerServiceImpl(writeJdbcTemplate, readJdbcTemplate, responseUtils);

    private Manager createTestManager() {
        return Manager.builder()
                .registrationNumber(1)
                .rootUserId(1001)
                .address("123 Test Street")
                .NIC("123456789V")
                .phoneNumber("0771234567")
                .build();
    }

    @Test
    @DisplayName("Create manager success scenario")
    void createManagerSuccess() {
        Manager manager = createTestManager();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = managerService.createManager(manager);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Create manager error scenario")
    void createManagerError() {
        Manager manager = createTestManager();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = managerService.createManager(manager);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Fetch manager by ID success scenario")
    void fetchManagerByIdSuccess() {
        Manager manager = createTestManager();
        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(manager);
        when(responseUtils.wrapSuccess(any(Manager.class), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = managerService.getManagerById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Fetch manager by ID error scenario")
    void fetchManagerByIdError() {
        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = managerService.getManagerById(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Delete manager success scenario")
    void deleteManagerSuccess() {
            when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
            when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK)))
                    .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));

            ResponseEntity<APIResponse> response = managerService.deleteManager(1);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Delete manager error scenario")
    void deleteManagerError() {
            when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
            when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                    .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));

            ResponseEntity<APIResponse> response = managerService.deleteManager(1);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertNotNull(response.getBody());
    }
}