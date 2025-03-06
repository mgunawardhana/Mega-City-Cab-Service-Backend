package com.megacity.backend.report_management.service;

import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    /**
     * Retrieves the tax details by status.
     *
     * @return a ResponseEntity containing the APIResponse with the tax details by status
     */
    ResponseEntity<APIResponse> getTaxDetailsByStatusWise();

    /**
     * Retrieves the total report details.
     *
     * @return a ResponseEntity containing the APIResponse with the total report details
     */
    ResponseEntity<APIResponse> getTotalReportDetails();
}
