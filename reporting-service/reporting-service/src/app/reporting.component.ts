import { Component, OnInit } from '@angular/core';
import { ReportingService } from './reporting.service';
import { Report } from './report.model';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  standalone: true,
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  reports: Report[] = [];

  constructor(private reportingService: ReportingService) { }

  ngOnInit(): void {
    this.loadReports();
  }

  loadReports(): void {
    this.reportingService.getAllReports().subscribe(
      (data: Report[]) => {
        this.reports = data;
      },
      error => {
        console.error('Failed to fetch reports:', error);
      }
    );
  }

  generateReport(reportType: string, content: string): void {
    this.reportingService.generateReport(reportType, content).subscribe(
      (data: Report) => {
        console.log('Report generated:', data);
        this.loadReports(); // Обновление списка отчетов после генерации нового
      },
      error => {
        console.error('Failed to generate report:', error);
      }
    );
  }

  // Другие методы компонента для управления отчетами
}
