package am.developers.reportingservice.service;

import am.developers.reportingservice.model.SaleData;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class PdfService {

        public void generateSalesReportPDF(List<SaleData> saleData, Date startDate, Date endDate) {
            // Настройка документа
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("SalesReport.pdf"));
                document.open();

                // Добавление заголовка
                document.add(new Paragraph("Sales Report"));
                document.add(new Paragraph("From: " + startDate.toString()));
                document.add(new Paragraph("To: " + endDate.toString()));
                document.add(new Paragraph(" "));

                // Добавление таблицы данных
                PdfPTable table = new PdfPTable(4); // Количество столбцов зависит от структуры SaleData
                table.addCell("Product ID");
                table.addCell("Quantity");
                table.addCell("Price");
                table.addCell("Sale Date");

                for (SaleData data : saleData) {
                    table.addCell(data.getProductId().toString());
                    table.addCell(data.getQuantity().toString());
                    table.addCell(data.getPrice().toString());
                    table.addCell(data.getSaleDate().toString());
                }

                document.add(table);

                // Закрытие документа
                document.close();
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }

