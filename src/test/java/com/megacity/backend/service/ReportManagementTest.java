package com.megacity.backend.service;

import com.megacity.backend.domain.entity.Report;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.report_management.service.impl.ReportServiceImpl;
import com.megacity.backend.util.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportManagementTest {

    private JdbcTemplate writeJdbcTemplate;
    private JdbcTemplate readJdbcTemplate;
    private ResponseUtil responseUtil;
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        writeJdbcTemplate = mock(JdbcTemplate.class);
        readJdbcTemplate = mock(JdbcTemplate.class);
        responseUtil = mock(ResponseUtil.class);
        reportService = new ReportServiceImpl(writeJdbcTemplate, readJdbcTemplate, responseUtil);
    }

    private Report createSampleReport() {
        return Report.builder()
                .taxes(1000.0)
                .tax_without_cost(800.0)
                .total_income(1200.0)
                .status("Completed")
                .row_count(5)
                .build();
    }

    @Test
    @DisplayName("Get tax details by status wise - Success scenario")
    void getTaxDetailsByStatusWiseSuccessTest() {
        List<Report> mockReports = Collections.singletonList(createSampleReport());
        when(readJdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(mockReports);
        when(responseUtil.wrapSuccess(any(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().result(mockReports).build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = reportService.getTaxDetailsByStatusWise();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockReports, response.getBody().getResult());
        verify(readJdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
        verify(responseUtil, times(1)).wrapSuccess(mockReports, HttpStatus.OK);
    }

    @Test
    @DisplayName("Get tax details by status wise - Error scenario")
    void getTaxDetailsByStatusWiseErrorTest() {
        // Arrange
        RuntimeException exception = new RuntimeException("Database connection failed");
        when(readJdbcTemplate.query(anyString(), any(RowMapper.class))).thenThrow(exception);
        when(responseUtil.wrapError(anyString(), eq(exception.getMessage()), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Error fetching status wise details")
                        .statusCode("500")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = reportService.getTaxDetailsByStatusWise();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error fetching status wise details", response.getBody().getStatusMessage());
        verify(readJdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
        verify(responseUtil, times(1)).wrapError(anyString(), eq(exception.getMessage()), eq(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    @DisplayName("Get total report details - Success scenario")
    void getTotalReportDetailsSuccessTest() {
        List<Report> mockReports = Collections.singletonList(createSampleReport());
        when(readJdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(mockReports);
        when(responseUtil.wrapSuccess(any(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().result(mockReports).build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = reportService.getTotalReportDetails();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockReports, response.getBody().getResult());
        verify(readJdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
        verify(responseUtil, times(1)).wrapSuccess(mockReports, HttpStatus.OK);
    }

    @Test
    @DisplayName("Get total report details - Error scenario")
    void getTotalReportDetailsErrorTest() {
        RuntimeException exception = new RuntimeException("Query execution failed");
        when(readJdbcTemplate.query(anyString(), any(RowMapper.class))).thenThrow(exception);
        when(responseUtil.wrapError(anyString(), eq(exception.getMessage()), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Error fetching revenue")
                        .statusCode("500")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = reportService.getTotalReportDetails();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error fetching revenue", response.getBody().getStatusMessage());
        verify(readJdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
        verify(responseUtil, times(1)).wrapError(anyString(), eq(exception.getMessage()), eq(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    @DisplayName("Test constructor injection")
    void constructorInjectionTest() {
        assertNotNull(reportService);
    }
}