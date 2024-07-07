import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Report } from './report.model'; // Модель Report, определенная в Angular

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private apiUrl = environment.apiUrl + '/reports'; // URL API Reporting Service

  constructor(private http: HttpClient) { }

  generateReport(reportType: string, content: string): Observable<Report> {
    return this.http.post<Report>(this.apiUrl, { reportType, content });
  }

  getAllReports(): Observable<Report[]> {
    return this.http.get<Report[]>(this.apiUrl);
  }
}
