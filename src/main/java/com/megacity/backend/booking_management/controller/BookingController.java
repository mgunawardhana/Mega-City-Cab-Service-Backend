package com.megacity.backend.booking_management.controller;


import com.megacity.backend.booking_management.service.BookingService;
import com.megacity.backend.booking_management.service.impl.StripeService;
import com.megacity.backend.domain.entity.Booking;
import com.megacity.backend.domain.request.ProductRequest;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.domain.response.StripeResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    @NonNull
    private final BookingService bookingService;

    @NonNull
    private final StripeService stripeService;

    @GetMapping("/advancedSearch")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<APIResponse> advancedSearch(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, @RequestParam(required = false) LocalDateTime bookingDate, @RequestParam(required = false) String pickupLocation, @RequestParam(required = false) String dropOffLocation, @RequestParam(required = false) String carNumber, @RequestParam(required = false) String driverId, @RequestParam(required = false) String status, @RequestParam(required = false) LocalDateTime createdDate) {
        log.info("advancedSearch {} {} {} {} {} {} {} {} {}", page, size, bookingDate, pickupLocation, dropOffLocation, carNumber, driverId, status, createdDate);
        var response = bookingService.advancedSearch(page, size, bookingDate, pickupLocation, dropOffLocation, carNumber, driverId, status, createdDate);
        log.info("advancedSearch {}", response);
        return response;
    }

    @PostMapping("/{bookingId}/{status}")
    public ResponseEntity<APIResponse> updateBookingFromDriverSide(@PathVariable String status, @PathVariable String bookingId) {
        log.info("updateBookingFromDriverSide {} {}", status, bookingId);
        log.info("updateBookingFromDriverSide");
        var resp = bookingService.updateBookingByDriverDetails(status, bookingId);
        log.info("updateBookingFromDriverSide");
        return resp;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkOutProducts(@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkProduct(productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) {
        bookingService.exportBookingsToExcel(response);
    }

    @GetMapping("/bookings")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<APIResponse> getAllBookings(@RequestParam Integer page, @RequestParam Integer size) {
        log.info("getAllBookings start");
        var response = bookingService.getAllBookings(page, size);
        log.info("getAllBookings {}", response);
        return response;
    }

    @GetMapping("/booking/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<APIResponse> getBookingById(@PathVariable String id) {
        log.info("getBookingById {}", id);
        var response = bookingService.getBookingById(Integer.valueOf(id));
        log.info("getBookingById {}", response);
        return response;
    }

    @DeleteMapping("/booking/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<APIResponse> deleteBooking(@PathVariable String id) {
        log.info("deleteBooking {}", id);
        var response = bookingService.deleteBooking(Integer.valueOf(id));
        log.info("deleteBooking {}", response);
        return response;
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<APIResponse> updateBooking(@RequestBody Booking booking) {
        log.info("updateBooking {}", booking);
        var response = bookingService.updateBooking(booking);
        log.info("updateBooking {}", response);
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerBooking(@RequestBody Booking booking) {
        log.info("registerBooking {}", booking);
        var response = bookingService.createBooking(booking);
        log.info("registerBooking {}", response);
        return response;
    }

    @PostMapping("/filter/{bookingId}")
    public ResponseEntity<APIResponse> fetchBookingByDriverId(@PathVariable String bookingId) {
        log.info("createBooking {}", bookingId);
        var response = bookingService.fetchBookingsByDriverIdAndStatus(bookingId);
        log.info("createBooking {}", response);
        return response;
    }
}
