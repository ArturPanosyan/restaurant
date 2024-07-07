package am.developers.reportingservice.scheduler;

import am.developers.reportingservice.service.ReportService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

@Configuration
@EnableScheduling
public class ReportScheduler {

    private ReportService reportService;

    @Scheduled(cron = "0 0 1 * * ?") // Каждый день в 1 час ночи
    public void generateDailySalesReport() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        reportService.generateSalesReport(yesterday.atStartOfDay(),
                yesterday.plusDays(1).atStartOfDay());
    }
}
