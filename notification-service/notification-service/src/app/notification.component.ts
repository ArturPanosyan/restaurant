import { Component, OnInit } from '@angular/core';
import { NotificationService } from './notification.service';
import { Notification } from './notification.model';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  standalone: true,
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  notifications: Notification[] = [];

  constructor(private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.loadNotifications();
  }

  loadNotifications(): void {
    const userId = 1; // Пример ID пользователя, замените на актуальный
    this.notificationService.getNotificationsByUserId(userId).subscribe(
      (data: Notification[]) => {
        this.notifications = data;
      },
      error => {
        console.error('Failed to fetch notifications:', error);
      }
    );
  }

  markAsRead(notificationId: number): void {
    this.notificationService.markAsRead(notificationId).subscribe(
      (data: Notification) => {
        console.log('Notification marked as read:', data);
        this.loadNotifications(); // Обновление списка уведомлений после изменения статуса
      },
      error => {
        console.error('Failed to mark notification as read:', error);
      }
    );
  }

  // Другие методы компонента для управления уведомлениями
}
