import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServicesService } from '../services.service';

@Component({
  selector: 'app-request-review',
  templateUrl: './request-review.component.html',
  styleUrls: ['./request-review.component.css']
})
export class RequestReviewComponent implements OnInit {
  rt = 0;
  reviews: any;
  noProductError = false;
  productExistsError = false;
  constructor(private service: ServicesService, private router: Router) { }

  ngOnInit(): void {
    this.rt = 0;
  }

  requestReview(form: any) {
    let newPost = {
      productName: form.value.productName,
      brand: form.value.brand,
      productCode: form.value.productCode,
    }
  
    this.service.reviewRequest(newPost).subscribe({
      next: (data) => {
        this.reviews = data;
        if (this.reviews.length === 0) {
          this.noProductError = true;
          this.productExistsError = false;
        } else {
          this.noProductError = false;
          this.productExistsError = true;
        }
        this.rt = 1;
      },
      error: (err) => {
        if (err.status === 404) {
          this.noProductError = true;
          this.productExistsError = false;
        }
        console.log(err);
      }
    });
  }
  

  logout() {
    this.service.logout();
    this.router.navigate(['login']);
  }

  loggedIn() {
    return this.service.isLogIn();
  }

  currentUser() {
    return this.service.getUser().firstName + " " + this.service.getUser().lastName;
  }


}
