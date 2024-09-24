package com.hotel.manager.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportPageResources {
	@GetMapping("/generalreport")
    public String reportResource() {
        return "report.html";
    }
}
