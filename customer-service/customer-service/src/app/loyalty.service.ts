// loyalty.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoyaltyService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getLoyaltyProgramByCustomerId(customerId: string) {
    return this.http.get(`${this.apiUrl}/loyalty?customerId=${customerId}`);
  }

  updateLoyaltyProgram(loyaltyProgram: any) {
    return this.http.put(`${this.apiUrl}/loyalty`, loyaltyProgram);
  }

  getCustomerPreferences(customerId: string) {
    return this.http.get(`${this.apiUrl}/preferences?customerId=${customerId}`);
  }

  updateCustomerPreferences(preferences: any) {
    return this.http.put(`${this.apiUrl}/preferences`, preferences);
  }
}
