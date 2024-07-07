import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RedisService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getValue(key: string) {
    return this.http.get(`${this.apiUrl}/redis/get?key=${key}`);
  }

  setValue(key: string, value: any) {
    return this.http.post(`${this.apiUrl}/redis/set`, { key, value });
  }
}
