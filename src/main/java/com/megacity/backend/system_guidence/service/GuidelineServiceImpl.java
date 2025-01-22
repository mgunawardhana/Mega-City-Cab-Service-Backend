package com.megacity.backend.system_guidence.service;

import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Guideline;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
            writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_GUIDELINE, guideline.getGuidanceId(), guideline.getTitle(), guideline.getDescription(), guideline.getCategory(), guideline.getPriority(), guideline.getRelatedTo());
            return responseUtil.wrapSuccess("Guideline added successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while registering guideline!");
            return responseUtil.wrapError("Error occurred while registering guideline!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> updateGuideline(Guideline guideline) {
        try {
            writeJdbcTemplate.update(SqlQuery.UpdateQuery.UPDATE_GUIDELINE, guideline.getGuidanceId(), guideline.getTitle(), guideline.getDescription(), guideline.getCategory(), guideline.getPriority(), guideline.getRelatedTo());
            return responseUtil.wrapSuccess("Guideline updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while updating guideline!");
            return responseUtil.wrapError("Error occurred while updating guideline!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> deleteGuideline(Long guidelineId) {
        try {
            writeJdbcTemplate.update(SqlQuery.DeleteQuery.DELETE_GUIDELINE, guidelineId);
            return responseUtil.wrapSuccess("Guideline deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while deleting guideline!");
            return responseUtil.wrapError("Error occurred while deleting guideline!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> fetchAllGuidelineRecords() {
        try {
            writeJdbcTemplate.update(SqlQuery.SelectQuery.FETCH_ALL_GUIDELINE);
            return responseUtil.wrapSuccess("fetched all guideline records successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching guideline records!");
            return responseUtil.wrapError("Error occurred while fetching guideline records!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> fetchGuidelineById(Long guidelineId) {
        try {
            Guideline guideline = readJdbcTemplate.queryForObject(SqlQuery.SelectQuery.FETCH_GUIDELINE_BY_ID, new Object[]{guidelineId}, (rs, rowNum) -> Guideline.builder().guidanceId(rs.getInt("guidance_id")).title(rs.getString("title")).description(rs.getString("description")).category(rs.getString("category")).priority(rs.getString("priority")).relatedTo(rs.getString("related_to")).build());

            writeJdbcTemplate.update(SqlQuery.SelectQuery.FETCH_ALL_GUIDELINE);
            return responseUtil.wrapSuccess(guideline, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching guideline records by id!");
            return responseUtil.wrapError("Error occurred while fetching guideline records by id!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
