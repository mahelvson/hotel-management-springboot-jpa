package com.hotel.manager.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.reports.BookingReport;
import com.hotel.manager.services.ReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/reports")
public class ReportResource {
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/booking/{id}")
	@Tag(name="Report")
	@Operation(
			summary = "Generate a report passing the booking ID",
			description = "Return the report of an specific booking by its ID",
			tags = {"report"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Booking report generated"),
					@ApiResponse(responseCode = "404", description = "Booking not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<BookingReport> getReport(@PathVariable Long id) {
		BookingReport report = reportService.generateReport(id);
		return ResponseEntity.ok(report);
	}

}
