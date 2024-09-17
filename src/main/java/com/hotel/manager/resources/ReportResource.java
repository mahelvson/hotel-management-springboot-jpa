package com.hotel.manager.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.reports.BookingReport;
import com.hotel.manager.services.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportResource {
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/booking/{id}")
	public ResponseEntity<BookingReport> getReport(@PathVariable Long id) {
		BookingReport report = reportService.generateReport(id);
		return ResponseEntity.ok(report);
	}

}
