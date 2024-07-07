import { Component, OnInit } from '@angular/core';
import { ReviewService } from './review.service';
import { Review } from './review.model';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  standalone: true,
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit {

  reviews: Review[] = [];
  newReview: Review = { customerId: 1, restaurantId: 1, comment: '', rating: 5, reviewDate: new Date() };

  constructor(private reviewService: ReviewService) { }

  ngOnInit(): void {
    this.loadReviews();
  }

  loadReviews(): void {
    const restaurantId = 1; // Пример ID ресторана, замените на актуальный
    this.reviewService.getReviewsByRestaurantId(restaurantId).subscribe(
      (data: Review[]) => {
        this.reviews = data;
      },
      error => {
        console.error('Failed to fetch reviews:', error);
      }
    );
  }

  addReview(): void {
    this.reviewService.createReview(this.newReview).subscribe(
      (data: Review) => {
        console.log('Review added successfully:', data);
        this.loadReviews(); // Обновление списка отзывов после добавления
      },
      error => {
        console.error('Failed to add review:', error);
      }
    );
  }

  // Другие методы компонента для управления отзывами
}
