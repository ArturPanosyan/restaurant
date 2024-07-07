import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class KafkaService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  sendMessage(topic: string, message: any) {
    return this.http.post(`${this.apiUrl}/kafka/send`, { topic, message });
  }
}
