import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Dish } from './dish.model';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  private apiUrl = `${environment.apiUrl}/menu`;

  constructor(private http: HttpClient) { }

  addDish(dish: Dish): Observable<Dish> {
    return this.http.post<Dish>(`${this.apiUrl}/add`, dish);
  }

  getDishesByCategory(category: string): Observable<Dish[]> {
    return this.http.get<Dish[]>(`${this.apiUrl}/category/${category}`);
  }

  searchDishesByName(name: string): Observable<Dish[]> {
    return this.http.get<Dish[]>(`${this.apiUrl}/search?name=${name}`);
  }
}
