import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ElasticsearchService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  search(index: string, query: any) {
    return this.http.post(`${this.apiUrl}/elasticsearch/search`, { index, query });
  }

  indexDocument(index: string, document: any) {
    return this.http.post(`${this.apiUrl}/elasticsearch/index`, { index, document });
  }
}
