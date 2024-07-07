import { Component, OnInit } from '@angular/core';
import { Order } from './order.model';
import { OrderService } from './order.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  standalone: true,
  styleUrls: ['./order.component.css']
})

export class OrderComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.orderService.getOrdersByStatus('CREATED').subscribe(
      (data) => {
        this.orders = data;
      },
      (error) => {
        console.error('Error loading orders: ', error);
      }
    );
  }

  createOrder(order: Order): void {
    this.orderService.createOrder(order).subscribe(
      (data) => {
        // Обработка успешного создания заказа
        this.loadOrders(); // Перезагрузка списка заказов после создания
      },
      (error) => {
        console.error('Error creating order: ', error);
      }
    );
  }
}
