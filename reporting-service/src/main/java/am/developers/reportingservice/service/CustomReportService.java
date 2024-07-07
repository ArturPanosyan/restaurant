package am.developers.reportingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomReportService {

    private JasperReportsGenerator jasperReportsGenerator;

    public byte[] generateCustomReport(String query) {
        // Логика генерации пользовательского отчета на основе SQL запроса
        // Использование JasperReports для формирования отчета в PDF или другом формате
        return jasperReportsGenerator.generateReport(query);
    }