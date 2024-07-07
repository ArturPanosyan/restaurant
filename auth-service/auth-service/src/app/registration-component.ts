import { Component } from '@angular/core';
import { AuthService } from './auth.service';
import { AuthRequest } from './auth-request.model';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent {

  constructor(private authService: AuthService) { }

  login(username: string, password: string): void {
    const authRequest: AuthRequest = { username, password };
    this.authService.login(authRequest).subscribe(
      (response) => {
        // Обработка успешного входа
      },
      (error) => {
        console.error('Error logging in: ', error);
      }
    );
  }

  register(username: string, password: string): void {
    const authRequest: AuthRequest = { username, password };
    this.authService.register(authRequest).subscribe(
      (response) => {
        // Обработка успешной регистрации
      },
      (error) => {
        console.error('Error registering: ', error);
      }
    );
  }
}
