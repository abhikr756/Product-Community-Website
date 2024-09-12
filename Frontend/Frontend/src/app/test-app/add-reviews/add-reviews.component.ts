import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Params, Router } from '@angular/router';

import { ServicesService } from '../services.service';

@Component({
  selector: 'app-add-reviews',

  templateUrl: './add-reviews.component.html',

  styleUrls: ['./add-reviews.component.css'],
})
export class AddReviewsComponent implements OnInit {
  rating: number = 1;

  ratingOptions: number[] = [1, 2, 3, 4, 5];

  review: any;

  pid: any;

  product: any;
  productCode: string= '';
  productName: string = ''

  constructor(
    private service: ServicesService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params: Params) => {
      this.pid = params['productId'];
    });
    this.service.getProduct(this.pid).subscribe((product: any) => {
      // this.product = product;
      this.productCode = product.productCode;
      this.productName = product.productName;
    });
  }

  errorMessage: string = '';

  addReview(form: any) {
    const reviewText = form.value.review;

    if (reviewText.trim() !== '') {
      if (reviewText.length >= 5 && reviewText.length <= 400) {
        const newPost = {
          heading: form.value.heading,

          rating: form.value.rating,

          review: reviewText,

          productName: form.value.productName,

          productCode: form.value.productCode,

          productId: localStorage.getItem('reviewProductId'),
        };

        this.service.addReview(newPost).subscribe(
          (data) => {
            this.router.navigate(['home']);
          },

          (err) => {
            this.errorMessage =
              'An error occurred while adding the review. Please try again.';
          }
        );
      } else {
        this.errorMessage = 'Review must be between 20 and 400 characters.';
      }
    } else {
      this.errorMessage = 'This field is required.';
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

  getRatingItems(rating: number): number[] {
    return Array(Math.floor(rating)).fill(0);
  }

  getRatingPercentage(rating: number): string {
    const percentage = (rating / 5) * 100;

    return `${percentage}%`;
  }

  //rating: number;

  preventNumberInput(event: KeyboardEvent) {
    const input = event.target as HTMLInputElement;

    const key = event.key;

    // Allow navigation keys (arrows, home, end) and backspace

    if (
      key.includes('Arrow') ||
      key === 'Backspace' ||
      key === 'Home' ||
      key === 'End'
    ) {
      return;
    }

    // Prevent input of numbers

    if (!isNaN(parseInt(key, 10))) {
      event.preventDefault();
    }
  }

  preventPaste(event: ClipboardEvent) {
    event.preventDefault();
  }

  preventDrop(event: DragEvent) {
    event.preventDefault();
  }

  //rating: number;

  setRating(value: number) {
    this.rating = value;
  }

  restrictInput(event: any) {
    event.target.value = this.rating; // Reset the input value to the current rating
  }
}
