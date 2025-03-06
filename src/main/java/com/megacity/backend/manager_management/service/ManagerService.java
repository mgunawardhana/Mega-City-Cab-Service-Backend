package com.megacity.backend.manager_management.service;

import com.megacity.backend.domain.entity.Manager;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface ManagerService {

    /**
     * Updates an existing manager in the system.
     *
     * @param manager the {@link Manager} object containing the updated manager details
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the update operation
     */
    ResponseEntity<APIResponse> UpdateManager(Manager manager);

    /**
     * Retrieves a specific manager by their ID.
     *
     * @param managerId the unique identifier of the manager to fetch
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the requested manager details
     */
    ResponseEntity<APIResponse> getManagerById(Integer managerId);

    /**
     * Retrieves all managers in the system.
     *
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the list of all managers
     */
    ResponseEntity<APIResponse> getAllManagers();

    /**
     * Creates a new manager in the system.
     *
     * @param manager the {@link Manager} object containing the manager details to be created
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the creation operation
     */
    ResponseEntity<APIResponse> createManager(Manager manager);

    /**
     * Deletes a manager from the system based on their ID.
     *
     * @param managerId the unique identifier of the manager to be deleted
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the deletion operation
     */
    ResponseEntity<APIResponse> deleteManager(Integer managerId);
}
