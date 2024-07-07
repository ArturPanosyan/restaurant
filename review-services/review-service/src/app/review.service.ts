// review.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular.common/http';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getReviewsForMenuItem(menuItemId: string) {
    return this.http.get(`${this.apiUrl}/reviews?menuItemId=${menuItemId}`);
  }

  addReview(review: any) {
    return this.http.post(`${this.apiUrl}/reviews`, review);
  }

  approveReview(reviewId: string) {
    return this.http.put(`${this.apiUrl}/reviews/approve`, { reviewId });
  }
}
