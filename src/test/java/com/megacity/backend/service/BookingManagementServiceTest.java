package com.megacity.backend.service;

import com.megacity.backend.booking_management.service.impl.BookingServiceImpl;
import com.megacity.backend.booking_management.service.impl.ExcelExportService;
import com.megacity.backend.domain.entity.Booking;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Slf4j
class BookingManagementServiceTest {

    private final ResponseUtil responseUtils = mock(ResponseUtil.class);
    private final JdbcTemplate writeJdbcTemplate = mock(JdbcTemplate.class);
    private final JdbcTemplate readJdbcTemplate = mock(JdbcTemplate.class);
    private final ExcelExportService excelService = mock(ExcelExportService.class);

    private final BookingServiceImpl bookingService = new BookingServiceImpl(writeJdbcTemplate, readJdbcTemplate, responseUtils, excelService);

    private Booking createBooking() {
        return Booking.builder()
                .bookingNumber(1L).bookingDate(LocalDateTime.now())
                .pickupLocation("Location A")
                .dropOffLocation("Location B")
                .carNumber("123ABC").taxes(BigDecimal.valueOf(100.0))
                .distance(200.0).estimatedTime(2.5)
                .taxWithoutCost(50.0)
                .totalAmount(BigDecimal.valueOf(150.0))
                .customerRegistrationNumber("CUST123")
                .driverId("DRIVER123").status("Active")
                .build();
    }

    @Test
    @DisplayName("Update booking success scenario")
    void updateBookingTest() {
        Booking booking = createBooking();
        when(writeJdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = bookingService.updateBooking(booking);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Create guideline error scenario")
    void createGuidelineError() {
        Booking booking = createBooking();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR))).thenReturn(new ResponseEntity<>(APIResponse.builder()
                .statusMessage("Database error").statusCode("500").build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = bookingService.createBooking(booking);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Database error", response.getBody().getStatusMessage());
        assertEquals("500", response.getBody().getStatusCode());
    }

    @Test
    @DisplayName("Create booking success scenario")
    void createBookingTest() {
        Booking booking = createBooking();
        when(writeJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(0);
        when(writeJdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = bookingService.createBooking(booking);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Get all bookings success scenario")
    void getAllBookingsTest() {
        List<Booking> bookings = List.of(createBooking());
        when(readJdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(bookings);
        when(responseUtils.wrapSuccess(any(), eq(HttpStatus.OK))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = bookingService.getAllBookings(0, 10);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Get booking by ID success scenario")
    void getBookingByIdTest() {
        Booking val = createBooking();
        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(val);
        when(responseUtils.wrapSuccess(any(), eq(HttpStatus.OK))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = bookingService.getBookingById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    @DisplayName("Delete booking success scenario")
    void deleteBookingTest() {
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = bookingService.deleteBooking(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Export bookings to Excel")
    void exportBookingsToExcelTest() throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        bookingService.exportBookingsToExcel(response);
        verify(response, times(1)).getOutputStream();
    }
}
