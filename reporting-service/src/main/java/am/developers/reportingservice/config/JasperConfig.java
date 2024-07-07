package am.developers.reportingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperConfig {

    @Bean
    public JasperReportsExporter jasperReportsExporter() {
        return new JasperReportsExporter();
    }

    @Bean
    public JasperReportsGenerator jasperReportsGenerator() {
        return new JasperReportsGenerator();
    }
}