package am.developers.reportingservice.controller;

import am.developers.reportingservice.model.SaleData;
import am.developers.reportingservice.service.SaleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleDataController {

    @Autowired
    private SaleDataService saleDataService;

    @PostMapping("/add")
    public ResponseEntity<?> addSaleData(@RequestBody SaleData saleData) {
        SaleData savedData = saleDataService.saveSaleData(saleData);
        return ResponseEntity.ok(savedData);
    }

    @GetMapping("/between")
    public ResponseEntity<?> getSalesBetweenDates(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<SaleData> sales = saleDataService.getSalesBetweenDates(startDate, endDate);
        return ResponseEntity.ok(sales);
    }
}