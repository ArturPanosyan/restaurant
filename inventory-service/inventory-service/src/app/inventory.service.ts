// inventory.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getInventoryByProductId(productId: string) {
    return this.http.get(`${this.apiUrl}/inventory?productId=${productId}`);
  }

  updateInventory(inventory: any) {
    return this.http.put(`${this.apiUrl}/inventory`, inventory);
  }
}
