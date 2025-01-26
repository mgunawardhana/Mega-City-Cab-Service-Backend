package com.megacity.backend.report_management.service;

import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    /**
     * Retrieves the total income for a specific day.
     *
     * @param selectedDate the date for which to retrieve the total income
     * @return a ResponseEntity containing the APIResponse with the total income
     */
    ResponseEntity<APIResponse> getTotalIncomeDayWise(String selectedDate);

    /**
     * Retrieves the total income for a specific month.
     *
     * @param SelectedMonth the month for which to retrieve the total income
     * @return a ResponseEntity containing the APIResponse with the total income
     */
    ResponseEntity<APIResponse> getTotalIncomeMonthWise(String SelectedMonth);

    /**
     * Retrieves the total income for a specific year.
     *
     * @param SelectedYear the year for which to retrieve the total income
     * @return a ResponseEntity containing the APIResponse with the total income
     */
    ResponseEntity<APIResponse> getTotalIncomeAnnually(String SelectedYear);

    /**
     * Retrieves the bookings for a specific customer.
     *
     * @param customerNIC the NIC of the customer for whom to retrieve the bookings
     * @return a ResponseEntity containing the APIResponse with the customer bookings
     */
    ResponseEntity<APIResponse> getCustomerWiseBookings(String customerNIC);

    /**
     * Retrieves the salary details for a specific driver.
     *
     * @param driverNIC the NIC of the driver for whom to retrieve the salary details
     * @return a ResponseEntity containing the APIResponse with the driver salary details
     */
    ResponseEntity<APIResponse> getDriverWiseSalary(String driverNIC);

    /**
     * Retrieves the salary details for a specific manager.
     *
     * @param managerNIC the NIC of the manager for whom to retrieve the salary details
     * @return a ResponseEntity containing the APIResponse with the manager salary details
     */
    ResponseEntity<APIResponse> getManagerWiseSalary(String managerNIC);

    /**
     * Retrieves the income details for a specific vehicle.
     *
     * @param vehicleNumber the number of the vehicle for which to retrieve the income details
     * @return a ResponseEntity containing the APIResponse with the vehicle income details
     */
    ResponseEntity<APIResponse> getVehicleWiseIncome(String vehicleNumber);

    /**
     * Retrieves the fuel consumption and expenses for a specific vehicle.
     *
     * @param vehicleNumber the number of the vehicle for which to retrieve the fuel consumption and expenses
     * @return a ResponseEntity containing the APIResponse with the vehicle fuel consumption and expenses
     */
    ResponseEntity<APIResponse> getVehicleWiseFuelConsumptionWisExpenses(String vehicleNumber);

    /**
     * Retrieves the total income.
     *
     * @return a ResponseEntity containing the APIResponse with the total income
     */
    ResponseEntity<APIResponse> getTotalIncome();

    /**
     * Retrieves the total expenses for a specific customer.
     *
     * @param customerNIC the NIC of the customer for whom to retrieve the total expenses
     * @return a ResponseEntity containing the APIResponse with the total expenses
     */
    ResponseEntity<APIResponse> getTotalExpenses(String customerNIC);
}
