package com.megacity.backend.booking_management.service;

import com.megacity.backend.domain.entity.Booking;
import com.megacity.backend.domain.response.APIResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**
 * Service interface for managing bookings in the MegaCity backend system.
 * Provides methods for creating, retrieving, updating, and deleting bookings,
 * as well as advanced search and export functionalities.
 */
public interface BookingService {

    ResponseEntity<APIResponse> updateBookingByDriverDetails(String bookingId);

    /**
     * Performs an advanced search for bookings based on multiple criteria.
     *
     * @param page            the page number for pagination (starting from 0)
     * @param size            the number of records per page
     * @param bookingDate     the date and time of the booking (optional)
     * @param pickupLocation  the pickup location of the booking (optional)
     * @param dropOffLocation the drop-off location of the booking (optional)
     * @param carNumber       the car number associated with the booking (optional)
     * @param driverId        the ID of the driver assigned to the booking (optional)
     * @param status          the status of the booking (optional)
     * @param createdDate     the creation date and time of the booking (optional)
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the search results
     */
    ResponseEntity<APIResponse> advancedSearch(int page, int size, LocalDateTime bookingDate, String pickupLocation, String dropOffLocation, String carNumber, String driverId, String status, LocalDateTime createdDate);

    /**
     * Retrieves all bookings with pagination support.
     *
     * @param page the page number for pagination (starting from 0)
     * @param size the number of records per page
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the list of bookings
     */
    ResponseEntity<APIResponse> getAllBookings(int page, int size);

    /**
     * Retrieves a specific booking by its ID.
     *
     * @param bookingId the unique identifier of the booking
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the booking details
     */
    ResponseEntity<APIResponse> getBookingById(Integer bookingId);

    /**
     * Creates a new booking in the system.
     *
     * @param booking the {@link Booking} object containing booking details
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the creation
     */
    ResponseEntity<APIResponse> createBooking(Booking booking);

    /**
     * Updates an existing booking in the system.
     *
     * @param booking the {@link Booking} object containing updated booking details
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the update
     */
    ResponseEntity<APIResponse> updateBooking(Booking booking);

    /**
     * Deletes a booking from the system by its ID.
     *
     * @param bookingId the unique identifier of the booking to delete
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the deletion
     */
    ResponseEntity<APIResponse> deleteBooking(Integer bookingId);

    /**
     * Exports all bookings to an Excel file and writes it to the HTTP response.
     *
     * @param response the {@link HttpServletResponse} to write the Excel file to
     */
    void exportBookingsToExcel(HttpServletResponse response);

    /**
     * Fetches bookings based on the driver ID and status.
     *
     * @param driverId the ID of the driver whose bookings are to be fetched
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the list of bookings
     */
    ResponseEntity<APIResponse> fetchBookingsByDriverIdAndStatus(String driverId);
}