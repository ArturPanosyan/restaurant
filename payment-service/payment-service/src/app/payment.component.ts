import { Component, OnInit } from '@angular/core';
import { PaymentService } from './payment.service';
import { Payment } from './payment.model';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  standalone: true,
  styleUrls: ['./payments.component.css']
})
export class PaymentsComponent implements OnInit {

  payments: Payment[] = [];

  constructor(private paymentService: PaymentService) { }

  ngOnInit(): void {
    this.loadPayments();
  }

  loadPayments(): void {
    const userId = 1; // Пример ID пользователя, замените на актуальный
    this.paymentService.getPaymentsByUserId(userId).subscribe(
      (data: Payment[]) => {
        this.payments = data;
      },
      error => {
        console.error('Failed to fetch payments:', error);
      }
    );
  }

  updatePaymentStatus(paymentId: number, status: string): void {
    this.paymentService.updatePaymentStatus(paymentId, status).subscribe(
      (data: Payment) => {
        console.log('Payment status updated:', data);
        this.loadPayments(); // Обновление списка платежей после изменения статуса
      },
      error => {
        console.error('Failed to update payment status:', error);
      }
    );
  }

  // Другие методы компонента для управления платежами
}
