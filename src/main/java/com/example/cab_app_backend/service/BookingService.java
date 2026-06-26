package com.example.cab_app_backend.service;

import com.example.cab_app_backend.model.Booking;
import com.example.cab_app_backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    // Add a new booking
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get booking by ID
    public Optional<Booking> getBookingById(String id) {
        return bookingRepository.findById(id);
    }
    public List<Booking> getBookingsByUserId(String userid) {

        return bookingRepository.findByUserid(userid);
    }

    // Get bookings by Driver ID
    public List<Booking> getBookingsByDriverId(String driverid) {
        return bookingRepository.findByDriverid(driverid);
    }

    // Get bookings by Status
    public List<Booking> getBookingsByStatus(int bookstatus) {
        return bookingRepository.findByBookstatus(bookstatus);
    }

    // Update booking details
    public Booking updateBooking(String id, Booking bookingDetails) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setUserid(bookingDetails.getUserid());
            booking.setCarid(bookingDetails.getCarid());
            booking.setDriverid(bookingDetails.getDriverid());
            booking.setLocation(bookingDetails.getLocation());
            booking.setTime(bookingDetails.getTime());
            booking.setBookstatus(bookingDetails.getBookstatus());
            booking.setTotalfee(bookingDetails.getTotalfee());
            booking.setPaymentstatus(bookingDetails.getPaymentstatus());
            booking.setIdNumber(bookingDetails.getIdNumber());
            return bookingRepository.save(booking);
        }).orElse(null);
    }

    // Delete a booking
    public String deleteBooking(String id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return "Booking deleted successfully!";
        }
        return "Booking not found!";
    }

    public Booking assignDriver(String id, String driverid) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setDriverid(driverid);
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    public Booking updateBookingStatus(String id, int status) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setBookstatus(status);
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }
    public Booking updateBookingStatus1(String id, int status) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setBookstatus(status);
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }
    public Booking updateTotalFee(String id, double totalfee) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setTotalfee(totalfee);
            return bookingRepository.save(booking);
        }).orElse(null);
    }
//update payment staus
    public Booking updatePaymentStatus(String id, int paymentstatus) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setPaymentstatus(paymentstatus);
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    public Booking updateTravelDistance(String id, int distance) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setTravelDistance(distance); // Fix here
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }
    public List<Booking> getBookingsByIdNumber(int idNumber) {
        return bookingRepository.findByIdNumber(idNumber);
    }
    public Booking updateBookingStatus3(String id, int status) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setBookstatus(status);
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    public Booking updateBookingStatusTo3(String id) {
        return updateBookingStatus3(id, 3);
    }



}
