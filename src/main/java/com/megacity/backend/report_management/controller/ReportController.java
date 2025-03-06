package com.megacity.backend.report_management.controller;

import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.report_management.service.ReportService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/report")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class ReportController {

    @NonNull
    private final ReportService reportService;

    @GetMapping("/get-business-report")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<APIResponse> getReport() {
        log.info("getReport start");
        var response = reportService.getTotalReportDetails();
        log.info("getReport {}", response);
        return response;
    }

    @GetMapping("/get-business-details-status-wise")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<APIResponse> getReportStatusWise() {
        log.info("getReportStatusWise start");
        var response = reportService.getTaxDetailsByStatusWise();
        log.info("getReportStatusWise {}", response);
        return response;
    }
}
