package com.megacity.backend.booking_management.service;

import com.megacity.backend.domain.entity.Booking;
import com.megacity.backend.domain.response.APIResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface BookingService {


    ResponseEntity<APIResponse> advancedSearch(int page, int size, LocalDateTime bookingDate, String pickupLocation, String dropOffLocation, String carNumber, String driverId, String status, LocalDateTime createdDate);

    /**
     * Retrieves all bookings.
     *
     * @return a ResponseEntity containing the APIResponse with the list of bookings
     */
    ResponseEntity<APIResponse> getAllBookings(int page, int size);

    /**
     * Retrieves a booking by its ID.
     *
     * @param bookingId the ID of the booking to be retrieved
     * @return a ResponseEntity containing the APIResponse with the booking details
     */
    ResponseEntity<APIResponse> getBookingById(Integer bookingId);

    /**
     * Creates a new booking.
     *
     * @param booking the booking to be created
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> createBooking(Booking booking);

    /**
     * Updates an existing booking.
     *
     * @param booking the booking to be updated
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> updateBooking(Booking booking);

    /**
     * Deletes a booking by its ID.
     *
     * @param bookingId the ID of the booking to be deleted
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> deleteBooking(Integer bookingId);


    void exportBookingsToExcel(HttpServletResponse response);

}
