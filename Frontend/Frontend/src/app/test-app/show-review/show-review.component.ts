import { Component, OnInit } from '@angular/core';

import { ServicesService } from '../services.service';

import { Router } from '@angular/router';

@Component({
  selector: 'app-show-review',

  templateUrl: './show-review.component.html',

  styleUrls: ['./show-review.component.css'],
})
export class ShowReviewComponent implements OnInit {
  constructor(private service: ServicesService, private router: Router) {}

  review: any;

  p: any;

  product: any;

  productName: any;

  productCode: any;

  averageRating: number = 0;

  ngOnInit(): void {
    const productId = localStorage.getItem('reviewProductId');

    this.service.showReviews(productId).subscribe({
      next: (data) => {
        this.p = localStorage.getItem('products');

        if (this.p != null) {
          this.p = JSON.parse(this.p);

          this.p.forEach((element: any) => {
            if (element.productId == productId) {
              this.product = element;
            }
          });
        }

        this.review = data;

        this.calculateAverageRating();
      },

      error: (err) => console.log(err),
    });
  }

  calculateAverageRating() {
    let sum = 0;

    let count = 0;

    this.review.forEach((r: any) => {
      if (r.approved && r.rating) {
        sum += r.rating;

        count++;
      }
    });

    if (count > 0) {
      this.averageRating = sum / count;
    }
  }

  logout() {
    this.service.logout();

    this.router.navigate(['login']);
  }

  loggedIn() {
    return this.service.isLogIn();
  }

  currentUser() {
    return (
      this.service.getUser().firstName + ' ' + this.service.getUser().lastName
    );
  }

  routerHome() {
    window.location.href = 'home';
  }

  getRatingItems(rating: number): number[] {
    const maxRating = 5;

    return Array.from({ length: Math.min(Math.floor(rating), maxRating) });
  }

  getRatingPercentage(rating: number): string {
    const percentage = (rating / 5) * 100;

    return `${percentage}%`;
  }
}
