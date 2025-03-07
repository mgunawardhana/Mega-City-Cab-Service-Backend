package com.megacity.backend.guideline_management.service.impl;

import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Guideline;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.guideline_management.service.GuidelineService;
import com.megacity.backend.util.ResponseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Service
public class GuidelineServiceImpl implements GuidelineService {

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final ResponseUtil responseUtil;

    public GuidelineServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate, @NonNull JdbcTemplate readJdbcTemplate, @NonNull ResponseUtil responseUtil) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> addNewGuideline(Guideline guideline) {
        try {
            writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_GUIDELINE, guideline.getTitle(), guideline.getDescription(), guideline.getCategory(), guideline.getPriority(), guideline.getRelatedTo());
            return responseUtil.wrapSuccess("Guideline added successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while registering guideline!");
            return responseUtil.wrapError("Error occurred while registering guideline!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> updateGuideline(Guideline guideline) {
        try {
            writeJdbcTemplate.update(SqlQuery.UpdateQuery.UPDATE_GUIDELINE, guideline.getTitle(), guideline.getDescription(), guideline.getCategory(), guideline.getPriority(), guideline.getRelatedTo(), guideline.getGuidanceId());
            return responseUtil.wrapSuccess("Guideline updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while updating guideline!");
            return responseUtil.wrapError("Error occurred while updating guideline!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> deleteGuideline(Long guidelineId) {
        log.error("guidelineId={}", guidelineId);
        try {
            writeJdbcTemplate.update(SqlQuery.DeleteQuery.DELETE_GUIDELINE, guidelineId);
            return responseUtil.wrapSuccess("Guideline deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while deleting guideline!");
            return responseUtil.wrapError("Error occurred while deleting guideline!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> fetchAllGuidelineRecords(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        try {
            List<Guideline> guidelines = writeJdbcTemplate.query(SqlQuery.SelectQuery.FETCH_ALL_GUIDELINE, new Object[]{size, page * size}, (rs, rowNum) -> Guideline.builder().guidanceId(rs.getInt("guidance_id")).title(rs.getString("title")).description(rs.getString("description")).category(rs.getString("category")).priority(rs.getString("priority")).relatedTo(rs.getString("related_to")).build());

            return responseUtil.wrapSuccess(guidelines, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching guideline records!", e);
            return responseUtil.wrapError("Error occurred while fetching guideline records!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> fetchGuidelineById(Long guidelineId) {
        try {
            Guideline guideline = readJdbcTemplate.queryForObject(SqlQuery.SelectQuery.FETCH_GUIDELINE_BY_ID, new Object[]{guidelineId}, (rs, rowNum) -> Guideline.builder().guidanceId(rs.getInt("guidance_id")).title(rs.getString("title")).description(rs.getString("description")).category(rs.getString("category")).priority(rs.getString("priority")).relatedTo(rs.getString("related_to")).build());

            return responseUtil.wrapSuccess(guideline, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching guideline records by id!", e);
            return responseUtil.wrapError("Error occurred while fetching guideline records by id!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
