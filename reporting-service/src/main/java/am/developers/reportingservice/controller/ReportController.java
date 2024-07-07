package am.developers.reportingservice.controller;

import am.developers.reportingservice.model.Report;
import am.developers.reportingservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @PostMapping
    public Report createReport(@RequestBody Report report) {
        return reportService.createReport(report);
    }

    @PostMapping
    public Report generateReport(@RequestParam String reportType, @RequestParam String content) {
        return reportService.generateReport(reportType, content);
    }


    @PostMapping
    public Report generateReport(@RequestBody String report) {
        return reportService.generateReport(report, report);
    }

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable Long id) {
        return reportService.getReportById(id);
    }
}