package com.megacity.backend.report_management.service.impl;

import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Booking;
import com.megacity.backend.domain.entity.Report;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.report_management.service.ReportService;
import com.megacity.backend.util.ResponseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final ResponseUtil responseUtil;

    public ReportServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate, @NonNull JdbcTemplate readJdbcTemplate, @NonNull ResponseUtil responseUtil) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> getTaxDetailsByStatusWise() {
        try {
            List<Report> query = readJdbcTemplate.query(SqlQuery.SelectQuery.GET_TAX_DETAILS_BY_STATUS_WISE, (rs, rowNum) -> Report.builder()
                    .taxes(rs.getDouble("total_taxes"))
                    .tax_without_cost(rs.getDouble("total_tax_without_cost"))
                    .total_income(rs.getDouble("total_amount"))
                    .status(rs.getString("status"))
                    .row_count(rs.getInt("row_count"))
                    .build());
            log.info("Fetched all status wise details successfully");
            return responseUtil.wrapSuccess(query, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching tax details", e);
            return responseUtil.wrapError("Error fetching status wise details", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<APIResponse> getTotalReportDetails() {
        try {
            List<Report> query = readJdbcTemplate.query(SqlQuery.SelectQuery.GET_TOTAL_REVENUE_BY_STATUS_ORDERED, (rs, rowNum) -> Report.builder()
                    .taxes(rs.getDouble("total_taxes"))
                    .tax_without_cost(rs.getDouble("total_tax_without_cost"))
                    .total_income(rs.getDouble("total_amount"))
                    .build());
            log.info("Fetched all revenue successfully");
            return responseUtil.wrapSuccess(query, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching revenue details", e);
            return responseUtil.wrapError("Error fetching revenue", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
