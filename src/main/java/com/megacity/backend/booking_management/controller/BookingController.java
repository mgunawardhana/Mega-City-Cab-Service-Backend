package com.megacity.backend.booking_management.controller;


import com.megacity.backend.booking_management.service.BookingService;
import com.megacity.backend.domain.entity.Booking;
import com.megacity.backend.domain.response.APIResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    @NonNull
    private final BookingService bookingService;

    @GetMapping("/bookings")
    public ResponseEntity<APIResponse> getAllBookings() {
        log.info("getAllBookings start");
        var response = bookingService.getAllBookings();
        log.info("getAllBookings {}", response);
        return response;
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<APIResponse> getBookingById(@PathVariable String id) {
        log.info("getBookingById {}", id);
        var response = bookingService.getBookingById(Integer.valueOf(id));
        log.info("getBookingById {}", response);
        return response;
    }

    @DeleteMapping("/booking/{id}")
    public ResponseEntity<APIResponse> deleteBooking(@PathVariable String id) {
        log.info("deleteBooking {}", id);
        var response = bookingService.deleteBooking(Integer.valueOf(id));
        log.info("deleteBooking {}", response);
        return response;
    }

    @PutMapping("/booking/update")
    public ResponseEntity<APIResponse> updateBooking(@RequestBody Booking booking) {
        log.info("updateBooking {}", booking);
        var response = bookingService.updateBooking(booking);
        log.info("updateBooking {}", response);
        return response;
    }

    @PostMapping("/booking/register")
    public ResponseEntity<APIResponse> registerBooking(@RequestBody Booking booking) {
        log.info("registerBooking {}", booking);
        var response = bookingService.createBooking(booking);
        log.info("registerBooking {}", response);
        return response;
    }
}
