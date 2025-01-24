package com.megacity.backend.booking_management.service.impl;

import com.megacity.backend.booking_management.service.BookingService;
import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Booking;
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
public class BookingServiceImpl implements BookingService {

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final ResponseUtil responseUtil;

    public BookingServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate, @NonNull JdbcTemplate readJdbcTemplate, @NonNull ResponseUtil responseUtil) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> getAllBookings() {
        try {
            readJdbcTemplate.query(SqlQuery.SelectQuery.GET_ALL_BOOKINGS, (rs, rowNum) -> Booking.builder()
                    .bookingNumber(rs.getLong("booking_number"))
                    .destinationDetails(rs.getString("destination_details"))
                    .bookingDate(rs.getTimestamp("booking_date").toLocalDateTime())
                    .pickupLocation(rs.getString("pickup_location"))
                    .dropOffLocation(rs.getString("drop_off_location"))
                    .carNumber(rs.getString("car_number"))
                    .fare(rs.getBigDecimal("fare"))
                    .taxes(rs.getBigDecimal("taxes"))
                    .discount(rs.getBigDecimal("discount"))
                    .totalAmount(rs.getBigDecimal("total_amount"))
                    .customerRegistrationNumber(rs.getString("customer_registration_number"))
                    .customerName(rs.getString("customer_name"))
                    .build());
            log.info("Fetched all bookings successfully");
            return responseUtil.wrapSuccess("Fetched all bookings successfully", HttpStatus.OK);
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
                    .destinationDetails(rs.getString("destination_details"))
                    .bookingDate(rs.getTimestamp("booking_date").toLocalDateTime())
                    .pickupLocation(rs.getString("pickup_location"))
                    .dropOffLocation(rs.getString("drop_off_location"))
                    .carNumber(rs.getString("car_number"))
                    .fare(rs.getBigDecimal("fare"))
                    .taxes(rs.getBigDecimal("taxes"))
                    .discount(rs.getBigDecimal("discount"))
                    .totalAmount(rs.getBigDecimal("total_amount"))
                    .customerRegistrationNumber(rs.getString("customer_registration_number"))
                    .customerName(rs.getString("customer_name"))
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
                    booking.getDestinationDetails(),
                    booking.getBookingDate(),
                    booking.getPickupLocation(),
                    booking.getDropOffLocation(),
                    booking.getCarNumber(),
                    booking.getFare(),
                    booking.getTaxes(),
                    booking.getDiscount(),
                    booking.getTotalAmount(),
                    booking.getCustomerRegistrationNumber(),
                    booking.getCustomerName());
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
            writeJdbcTemplate.update(SqlQuery.UpdateQuery.UPDATE_BOOKING,
                    booking.getDestinationDetails(),
                    booking.getBookingDate(),
                    booking.getPickupLocation(),
                    booking.getDropOffLocation(),
                    booking.getCarNumber(),
                    booking.getFare(),
                    booking.getTaxes(),
                    booking.getDiscount(),
                    booking.getTotalAmount(),
                    booking.getCustomerRegistrationNumber(),
                    booking.getCustomerName(),
                    booking.getBookingNumber());
            log.info("Booking updated successfully");
            return responseUtil.wrapSuccess("Booking updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating booking", e);
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
