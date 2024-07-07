// delivery.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular.common/http';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  trackDelivery(orderId: string) {
    return this.http.get(`${this.apiUrl}/deliveries?orderId=${orderId}`);
  }

  updateDeliveryStatus(orderId: string, status: string) {
    return this.http.put(`${this.apiUrl}/deliveries/status`, { orderId, status });
  }
}
