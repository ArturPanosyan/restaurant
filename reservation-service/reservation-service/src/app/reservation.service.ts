import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Reservation } from './reservation.model'; // Модель Reservation, определенная в Angular

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private apiUrl = environment.apiUrl + '/reservations'; // URL API Reservation Service

  constructor(private http: HttpClient) { }

  createReservation(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, reservation);
  }

  getReservationsByCustomerId(customerId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/customer/${customerId}`);
  }

  getReservationsByTableId(tableId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/table/${tableId}`);
  }

  updateReservationStatus(reservationId: number, status: string): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${reservationId}/status`, { status });
  }
}
