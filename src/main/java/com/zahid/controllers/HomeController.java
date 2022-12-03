package com.zahid.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zahid.services.CoronaDataService;

@Controller
public class HomeController {

    @Autowired
    private CoronaDataService coronaDataService;

    @GetMapping("/")
    public String home(Model model) {
        long totalReportedCases = coronaDataService.getAllData().stream().mapToLong(record -> record.getLatestTotalCases()).sum();
        model.addAttribute("locationData", coronaDataService.getAllData());
        // model.addAttribute("totalReportedCases", coronaDataService.getTotalReportedCases());
        model.addAttribute("totalReportedCases", totalReportedCases);

        return "home";
    }
}
