import { Component, OnInit } from '@angular/core';
import { ReservationService } from './reservation.service';
import { Reservation } from './reservation.model';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  standalone: true,
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {

  reservations: Reservation[] = [];

  constructor(private reservationService: ReservationService) { }

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    const customerId = 1; // Пример ID клиента, замените на актуальный
    this.reservationService.getReservationsByCustomerId(customerId).subscribe(
      (data: Reservation[]) => {
        this.reservations = data;
      },
      error => {
        console.error('Failed to fetch reservations:', error);
      }
    );
  }

  updateReservationStatus(reservationId: number, status: string): void {
    this.reservationService.updateReservationStatus(reservationId, status).subscribe(
      (data: Reservation) => {
        console.log('Reservation status updated:', data);
        this.loadReservations(); // Обновление списка бронирований после изменения статуса
      },
      error => {
        console.error('Failed to update reservation status:', error);
      }
    );
  }

  // Другие методы компонента для управления бронированиями
}
