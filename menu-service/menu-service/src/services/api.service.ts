import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  sendMessageToKafka(topic: string, message: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/kafka/send/${topic}`, message);
  }

  // Дополнительные методы для работы с Kafka, например, получение сообщений и других операций
}
