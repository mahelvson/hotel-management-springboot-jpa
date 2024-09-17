package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.enums.PaymentMethod;
import com.hotel.manager.enums.PaymentStatus;
import com.hotel.manager.reports.BookingReport;
import com.hotel.manager.repositories.BookingRepository;

@Service
public class ReportService {

    @Autowired
    private BookingRepository bookingRepository;

    public BookingReport generateReport(Long bookingId) {

        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (!bookingOpt.isPresent()) {
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }

        Booking booking = bookingOpt.get();

        List<Integer> roomNumbers = booking.getRooms()
                                           .stream()
                                           .map(room -> room.getRoomNumber())
                                           .collect(Collectors.toList());


        PaymentStatus paymentStatus = booking.getPayment() != null
                ? booking.getPayment().getPaymentStatus()
                : PaymentStatus.PENDING;

        PaymentMethod paymentMethod = booking.getPayment() != null
                ? booking.getPayment().getMethod()
                : PaymentMethod.UNDEFINED;

        return new BookingReport(
                booking.getId(),
                booking.getClient().getName(),
                booking.getClient().getEmail(),
                booking.getDateCheckIn(),
                booking.getDateCheckOut(),
                booking.getTotal(),
                roomNumbers,
                paymentStatus,
                paymentMethod
        );
    }
}
