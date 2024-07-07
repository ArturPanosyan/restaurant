package am.developers.reportingservice.service;

import am.developers.reportingservice.model.Report;
import am.developers.reportingservice.model.SaleData;
import am.developers.reportingservice.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private KafkaTemplate<String, Object> kafkaTemplate;
    private ElasticsearchTemplate elasticsearchTemplate;
    private static final String REPORT_TOPIC = "report_notifications";
    private static PdfService pdfService;

    public void generateSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        // Логика генерации отчета о продажах
        List<SaleData> saleData = elasticsearchTemplate.queryForList(
                new NativeSearchQueryBuilder()
                        .withQuery(QueryBuilders.rangeQuery("date").gte(startDate).lte(endDate))
                        .build(),
                SaleData.class);

        // Генерация PDF отчета
        pdfService.generateSalesReportPDF(saleData, startDate, endDate);
    }

    @KafkaListener(topics = REPORT_TOPIC, groupId = "report_group")
    public void listenReportEvents(String eventType, Object event) {
        switch (eventType) {
            case "OrderCompleted":
                // Логика обработки события завершения заказа для отчетов
                break;
            // другие кейсы
        }
    }
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    public Report generateReport(String reportType, String content) {
        Report report = new Report();
        report.setReportType(reportType);
        report.setGeneratedAt(LocalDateTime.now());
        report.setContent(content);
        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReportById(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow();
    }

    public byte[] generateReport(String format) {
        switch (format.toLowerCase()) {
            case "pdf":
                // Логика генерации PDF отчета
                break;
            case "excel":
                // Логика генерации Excel отчета
                break;
            case "csv":
                // Логика генерации CSV отчета
                break;
            default:
                throw new IllegalArgumentException("Unsupported format: " + format);
        }
        return new byte[0];
    }

    @Scheduled(cron = "0 0 1 * * ?") // Ежедневно в 1 час ночи
    public void sendDailyReport() {
        byte[] report = generateReport("pdf");
        sendEmailWithAttachment("report@example.com", "Daily Report", "Please find the daily report attached.", report);
    }

    private void sendEmailWithAttachment(String to, String subject, String body, byte[] attachment) {
        // Логика отправки электронной почты с вложением
    }
}