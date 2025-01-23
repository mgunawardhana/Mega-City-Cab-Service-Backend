package com.megacity.backend.manager_management.service.impl;

import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Customer;
import com.megacity.backend.domain.entity.Manager;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.manager_management.service.ManagerService;
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
public class ManagerServiceImpl implements ManagerService {

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final ResponseUtil responseUtil;

    public ManagerServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate, @NonNull JdbcTemplate readJdbcTemplate, @NonNull ResponseUtil responseUtil) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> UpdateManager(Manager manager) {
        try {
            writeJdbcTemplate.update(SqlQuery.UpdateQuery.UPDATE_MANAGER,
                    manager.getManagerRegistrationNumber(),
                    manager.getRootUserId(),
                    manager.getManagerAddress(),
                    manager.getManagerNIC(),
                    manager.getPhoneNumber()
            );
            log.info("Updated manager successfully");
            return responseUtil.wrapSuccess("Updated manager successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating manager", e);
            return responseUtil.wrapError("Error updating manager", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getManagerById(Integer managerId) {
        try{
            Manager manager = readJdbcTemplate.queryForObject(SqlQuery.SelectQuery.GET_MANAGER_BY_ID, new Object[]{managerId}, (rs, rowNum) -> Manager.builder().managerRegistrationNumber(rs.getInt("manager_registration_number")).rootUserId(rs.getInt("root_user_id")).managerAddress(rs.getString("manager_address")).managerNIC(rs.getString("manager_nic")).phoneNumber(rs.getString("phone_number")).build());
            log.info("Fetched manager successfully");
            return responseUtil.wrapSuccess(manager, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching manager", e);
            return responseUtil.wrapError("Error fetching manager", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getAllManagers() {
        try{
            List<Manager> managers = readJdbcTemplate.query(SqlQuery.SelectQuery.GET_ALL_MANAGERS,
                    (rs, rowNum) -> Manager.builder()
                            .managerRegistrationNumber(rs.getInt("manager_registration_number"))
                            .rootUserId(rs.getInt("root_user_id"))
                            .managerAddress(rs.getString("manager_address"))
                            .managerNIC(rs.getString("manager_nic"))
                            .phoneNumber(rs.getString("phone_number")).build());
            log.info("Fetched all managers successfully");
            return responseUtil.wrapSuccess(managers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching managers", e);
            return responseUtil.wrapError("Error fetching managers", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> createManager(Manager manager) {
        try{
            writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_MANAGER,
                    manager.getRootUserId(),
                    manager.getManagerAddress(),
                    manager.getManagerNIC(),
                    manager.getPhoneNumber());
            log.info("Manager created successfully");
            return responseUtil.wrapSuccess("Manager created successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error creating manager", e);
            return responseUtil.wrapError("Error creating manager", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> deleteManager(Integer managerId) {
        try{
            writeJdbcTemplate.update(SqlQuery.DeleteQuery.DELETE_MANAGER, managerId);
            log.info("Manager deleted successfully");
            return responseUtil.wrapSuccess("Manager deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting manager", e);
            return responseUtil.wrapError("Error deleting manager", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
