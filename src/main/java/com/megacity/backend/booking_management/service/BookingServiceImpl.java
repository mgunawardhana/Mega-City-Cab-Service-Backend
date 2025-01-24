package com.megacity.backend.booking_management.service;

import com.megacity.backend.domain.entity.Booking;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService{

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
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getBookingById(Integer bookingId) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> createBooking(Booking booking) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> updateBooking(Booking booking) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> deleteBooking(Integer bookingId) {
        return null;
    }
}
