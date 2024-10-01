package com.hotel.manager.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.entities.Client;
import com.hotel.manager.enums.PaymentMethod;
import com.hotel.manager.enums.PaymentStatus;
import com.hotel.manager.reports.BookingReport;
import com.hotel.manager.repositories.BookingRepository;
import com.hotel.manager.repositories.ClientRepository;

@Service
public class ReportService {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired ClientRepository clientRepository;

    public BookingReport generateReport(Long bookingId) {

        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (!bookingOpt.isPresent()) {
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }

        Booking booking = bookingOpt.get();
        
        Integer roomNumber = booking.getRoom().getRoomNumber();

        PaymentStatus paymentStatus = booking.getPayment() != null
                ? booking.getPayment().getPaymentStatus()
                : PaymentStatus.PENDING;

        PaymentMethod paymentMethod = booking.getPayment() != null
                ? booking.getPayment().getMethod()
                : PaymentMethod.UNDEFINED;
        
      
        Client client = booking.getClient();
        return new BookingReport(
                booking.getId(),
                client.getName(),
                client.getEmail(),
                booking.getDateCheckIn(),
                booking.getDateCheckOut(),
                booking.getTotal(),
                roomNumber,
                paymentStatus,
                paymentMethod
        );
    }
}
