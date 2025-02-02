// payment.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  processPayment(token: string, amount: number) {
    return this.http.post(`${this.apiUrl}/payments`, { token, amount });
  }
}
