package com.megacity.backend.booking_management.service.impl;

import com.megacity.backend.booking_management.service.BookingService;
import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Booking;
import com.megacity.backend.domain.entity.Guideline;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final ResponseUtil responseUtil;

    @NonNull
    private final ExcelExportService excelExportService;

    public BookingServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate,
                              @NonNull JdbcTemplate readJdbcTemplate,
                              @NonNull ResponseUtil responseUtil,
                              @NonNull ExcelExportService excelExportService) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
        this.excelExportService = excelExportService;
    }

    @Override
    public ResponseEntity<APIResponse> updateBookingByDriverDetails(String booking_id, String status) {
        log.info("Updating booking status for bookingId: {} with status: {}", booking_id, status);

        try {
            Long bookingIdLong = Long.parseLong(booking_id);

            Booking updatedBooking = readJdbcTemplate.queryForObject(
                    SqlQuery.UpdateQuery.UPDATE_BOOKING_STATUS_FROM_DRIVER_SIDE,
                    new Object[]{status, bookingIdLong},
                    (rs, rowNum) -> Booking.builder()
                            .bookingNumber(rs.getLong("booking_number"))
                            .status(rs.getString("status"))
                            .updatedDate(rs.getObject("updated_date", LocalDateTime.class))
                            .driverId(rs.getString("driver_id"))
                            .build()
            );

            System.out.println(updatedBooking);

            return responseUtil.wrapSuccess("Booking updated successfully", HttpStatus.OK);

        } catch (NumberFormatException e) {
            log.error("Invalid booking ID format: {}", booking_id, e);
            return responseUtil.wrapError("Invalid Input", "Booking ID must be numeric", HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            log.error("Database error updating booking for bookingId: {}", booking_id, e);
            return responseUtil.wrapError("Database Error", "Error updating booking status", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Unexpected error updating booking for bookingId: {}", booking_id, e);
            return responseUtil.wrapError("Unexpected Error", "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void exportBookingsToExcel(HttpServletResponse response) {
        try {
            List<Booking> bookings = readJdbcTemplate.query(SqlQuery.SelectQuery.GET_ALL_BOOKINGS_WITHOUT_PAGINATION, (rs, rowNum) ->
                    Booking.builder().bookingNumber(rs.getLong("booking_number"))
                            .bookingDate(rs.getTimestamp("booking_date").toLocalDateTime())
                            .pickupLocation(rs.getString("pickup_location"))
                            .dropOffLocation(rs.getString("drop_off_location"))
                            .carNumber(rs.getString("car_number"))
                            .taxes(rs.getBigDecimal("taxes"))
                            .distance(rs.getDouble("distance"))
                            .estimatedTime(rs.getDouble("estimatedTime"))
                            .taxWithoutCost(rs.getDouble("tax_without_cost"))
                            .totalAmount(rs.getBigDecimal("total_amount"))
                            .customerRegistrationNumber(rs.getString("customer_registration_number"))
                            .driverId(rs.getString("driver_id")).status(rs.getString("status"))
                            .createdDate(rs.getTimestamp("created_date").toLocalDateTime())
                            .updatedDate(rs.getTimestamp("updated_date").toLocalDateTime())
                            .build());

            byte[] excelData = excelExportService.generateExcel(bookings);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bookings.xlsx");
            response.getOutputStream().write(excelData);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("Error exporting bookings to Excel", e);
        }
    }

    @Override
    public ResponseEntity<APIResponse> fetchBookingsByDriverIdAndStatus(String driverId) {
        try {
            List<Booking> bookings = readJdbcTemplate.query(SqlQuery.SelectQuery.FIND_BOOKING_BY_DRIVER_ID,
                    new Object[]{driverId}, (rs, rowNum) -> Booking.builder()
                            .bookingNumber(rs.getLong("booking_number"))
                            .bookingDate(rs.getTimestamp("booking_date").toLocalDateTime())
                            .pickupLocation(rs.getString("pickup_location"))
                            .dropOffLocation(rs.getString("drop_off_location"))
                            .distance(rs.getDouble("distance"))
                            .estimatedTime(rs.getDouble("estimatedTime"))
                            .totalAmount(rs.getBigDecimal("total_amount"))
                            .customerRegistrationNumber(rs.getString("customer_registration_number"))
                            .driverId(rs.getString("driver_id"))
                            .status(rs.getString("status")).build());

            System.out.println(bookings);

            log.info("Fetched bookings successfully for driverId: {}", driverId);
            return responseUtil.wrapSuccess(bookings, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching bookings for driverId: {} ", driverId, e);
            return responseUtil.wrapError("Error fetching bookings", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> advancedSearch(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, @RequestParam(required = false) LocalDateTime bookingDate, @RequestParam(required = false) String pickupLocation, @RequestParam(required = false) String dropOffLocation, @RequestParam(required = false) String carNumber, @RequestParam(required = false) String driverId, @RequestParam(required = false) String status, @RequestParam(required = false) LocalDateTime createdDate) {

        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM booking WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (bookingDate != null) {
                queryBuilder.append(" AND booking_date = ?");
                params.add(bookingDate);
            }
            if (pickupLocation != null) {
                queryBuilder.append(" AND pickup_location LIKE ?");
                params.add("%" + pickupLocation + "%");
            }
            if (dropOffLocation != null) {
                queryBuilder.append(" AND drop_off_location LIKE ?");
                params.add("%" + dropOffLocation + "%");
            }
            if (carNumber != null) {
                queryBuilder.append(" AND car_number = ?");
                params.add(carNumber);
            }
            if (driverId != null) {
                queryBuilder.append(" AND driver_id = ?");
                params.add(driverId);
            }
            if (status != null) {
                queryBuilder.append(" AND status = ?");
                params.add(status);
            }
            if (createdDate != null) {
                queryBuilder.append(" AND created_date = ?");
                params.add(createdDate);
            }

            queryBuilder.append(" LIMIT ? OFFSET ?");
            params.add(size);
            params.add(page * size);

            List<Booking> query = readJdbcTemplate.query(queryBuilder.toString(), params.toArray(), (rs, rowNum) -> Booking.builder().bookingNumber(rs.getLong("booking_number")).bookingDate(rs.getTimestamp("booking_date").toLocalDateTime()).pickupLocation(rs.getString("pickup_location")).dropOffLocation(rs.getString("drop_off_location")).carNumber(rs.getString("car_number")).taxes(rs.getBigDecimal("taxes")).distance(rs.getDouble("distance")).estimatedTime(rs.getDouble("estimatedTime")).taxWithoutCost(rs.getDouble("tax_without_cost")).totalAmount(rs.getBigDecimal("total_amount")).customerRegistrationNumber(rs.getString("customer_registration_number")).driverId(rs.getString("driver_id")).status(rs.getString("status")).createdDate(rs.getTimestamp("created_date").toLocalDateTime()).updatedDate(rs.getTimestamp("updated_date").toLocalDateTime()).build());

            log.info("Fetched filtered bookings successfully");
            return responseUtil.wrapSuccess(query, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching filtered bookings", e);
            return responseUtil.wrapError("Error fetching bookings", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getAllBookings(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        try {
            List<Booking> query = readJdbcTemplate.query(SqlQuery.SelectQuery.GET_ALL_BOOKINGS, new Object[]{size, page * size}, (rs, rowNum) -> Booking.builder().bookingNumber(rs.getLong("booking_number")).bookingDate(rs.getTimestamp("booking_date").toLocalDateTime()).pickupLocation(rs.getString("pickup_location")).dropOffLocation(rs.getString("drop_off_location")).carNumber(rs.getString("car_number")).taxes(rs.getBigDecimal("taxes")).distance(rs.getDouble("distance")).estimatedTime(rs.getDouble("estimatedTime")).taxWithoutCost(rs.getDouble("tax_without_cost")).totalAmount(rs.getBigDecimal("total_amount")).customerRegistrationNumber(rs.getString("customer_registration_number")).driverId(rs.getString("driver_id")).status(rs.getString("status")).build());
            log.info("Fetched all bookings successfully");
            return responseUtil.wrapSuccess(query, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching bookings", e);
            return responseUtil.wrapError("Error fetching bookings", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getBookingById(Integer bookingId) {
        try {
            Booking booking = readJdbcTemplate.queryForObject(SqlQuery.SelectQuery.GET_BOOKING_BY_ID, new Object[]{bookingId}, (rs, rowNum) -> Booking.builder().bookingNumber(rs.getLong("booking_number")).bookingDate(rs.getTimestamp("booking_date").toLocalDateTime()).pickupLocation(rs.getString("pickup_location")).dropOffLocation(rs.getString("drop_off_location")).carNumber(rs.getString("car_number")).taxes(rs.getBigDecimal("taxes")).distance(rs.getDouble("distance")).estimatedTime(rs.getDouble("estimatedTime")).taxWithoutCost(rs.getDouble("tax_without_cost")).totalAmount(rs.getBigDecimal("total_amount")).customerRegistrationNumber(rs.getString("customer_registration_number")).driverId(rs.getString("driver_id")).status(rs.getString("status")).build());
            log.info("Fetched booking successfully");
            return responseUtil.wrapSuccess(booking, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching booking", e);
            return responseUtil.wrapError("Error fetching booking", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> createBooking(Booking booking) {
        try {

            Integer count = writeJdbcTemplate.queryForObject(SqlQuery.InsertQuery.VALIDATE_BOOKING, Integer.class,
                    booking.getCarNumber(),
                    booking.getBookingDate());

            if (count != null && count > 0) {
                log.warn("Booking conflict: Car {} is already booked until the estimated time is completed", booking.getCarNumber());
                return responseUtil.wrapError(
                        "Booking conflict: Car is already booked until the previous trip's estimated time is completed.",
                        "Please select a different time slot.",
                        HttpStatus.CONFLICT
                );
            }

            writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_BOOKING, booking.getBookingDate(), booking.getPickupLocation(), booking.getDropOffLocation(), booking.getCarNumber(), booking.getTaxes(), booking.getDistance(), booking.getEstimatedTime(), booking.getTaxWithoutCost(), booking.getTotalAmount(), booking.getCustomerRegistrationNumber(), booking.getDriverId(), booking.getStatus());

            log.info("Booking created successfully for car {}", booking.getCarNumber());
            return responseUtil.wrapSuccess("Booking created successfully", HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error creating booking", e);
            return responseUtil.wrapError("Error creating booking", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<APIResponse> updateBooking(Booking booking) {
        try {
            if (booking.getBookingNumber() == null) {
                return responseUtil.wrapError("Booking number is required", "Invalid request", HttpStatus.BAD_REQUEST);
            }
            LocalDateTime updatedDate = LocalDateTime.now();

            writeJdbcTemplate.update(SqlQuery.UpdateQuery.UPDATE_BOOKING, booking.getBookingDate(), booking.getPickupLocation(), booking.getDropOffLocation(), booking.getCarNumber(), booking.getTaxes(), booking.getDistance(), booking.getEstimatedTime(), booking.getTaxWithoutCost(), booking.getTotalAmount(), booking.getCustomerRegistrationNumber(), booking.getDriverId(), booking.getStatus(), updatedDate, booking.getBookingNumber());

            log.info("Booking updated successfully for booking number: {}", booking.getBookingNumber());
            return responseUtil.wrapSuccess("Booking updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating booking: {}", e.getMessage(), e);
            return responseUtil.wrapError("Error updating booking", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> deleteBooking(Integer bookingId) {
        try {
            writeJdbcTemplate.update(SqlQuery.DeleteQuery.DELETE_BOOKING_BY_ID, bookingId);
            log.info("Booking deleted successfully");
            return responseUtil.wrapSuccess("Booking deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting booking", e);
            return responseUtil.wrapError("Error deleting booking", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
