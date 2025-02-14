package com.megacity.backend.booking_management.service.impl;

import com.megacity.backend.booking_management.service.BookingService;
import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Booking;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

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

    public BookingServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate, @NonNull JdbcTemplate readJdbcTemplate,
                              @NonNull ResponseUtil responseUtil, @NonNull ExcelExportService excelExportService) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
        this.excelExportService = excelExportService;
    }

    @Override
    public void exportBookingsToExcel(HttpServletResponse response) {
        try {
            List<Booking> bookings = readJdbcTemplate.query(SqlQuery.SelectQuery.GET_ALL_BOOKINGS_WITHOUT_PAGINATION, (rs, rowNum) -> Booking.builder()
                    .bookingNumber(rs.getLong("booking_number"))
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
                    .driverId(rs.getString("driver_id"))
                    .status(rs.getString("status"))
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
    public ResponseEntity<APIResponse> getAllBookings(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "100") int size) {
        try {
            List<Booking> query = readJdbcTemplate.query(SqlQuery.SelectQuery.GET_ALL_BOOKINGS,new Object[]{size, page*size}, (rs, rowNum) -> Booking.builder()
                    .bookingNumber(rs.getLong("booking_number"))
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
                    .driverId(rs.getString("driver_id"))
                    .status(rs.getString("status"))
                    .build());
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
            Booking booking = readJdbcTemplate.queryForObject(SqlQuery.SelectQuery.GET_BOOKING_BY_ID, new Object[]{bookingId}, (rs, rowNum) -> Booking.builder()
                    .bookingNumber(rs.getLong("booking_number"))
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
                    .driverId(rs.getString("driver_id"))
                    .status(rs.getString("status"))
                    .build());
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
            writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_BOOKING,
                    booking.getBookingDate(),
                    booking.getPickupLocation(),
                    booking.getDropOffLocation(),
                    booking.getCarNumber(),
                    booking.getTaxes(),
                    booking.getDistance(),
                    booking.getEstimatedTime(),
                    booking.getTaxWithoutCost(),
                    booking.getTotalAmount(),
                    booking.getCustomerRegistrationNumber(),
                    booking.getDriverId(),
                    booking.getStatus());
            log.info("Booking created successfully");
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

            writeJdbcTemplate.update(SqlQuery.UpdateQuery.UPDATE_BOOKING,
                    booking.getBookingDate(),
                    booking.getPickupLocation(),
                    booking.getDropOffLocation(),
                    booking.getCarNumber(),
                    booking.getTaxes(),
                    booking.getDistance(),
                    booking.getEstimatedTime(),
                    booking.getTaxWithoutCost(),
                    booking.getTotalAmount(),
                    booking.getCustomerRegistrationNumber(),
                    booking.getDriverId(),
                    booking.getStatus(),
                    updatedDate,
                    booking.getBookingNumber()
            );

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
