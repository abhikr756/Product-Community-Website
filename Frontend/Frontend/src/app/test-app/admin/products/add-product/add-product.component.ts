import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServicesService } from 'src/app/test-app/services.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  errorMessage: string = '';

  constructor(private service: ServicesService, private router: Router) { }

  ngOnInit(): void {
    
  }

  addProduct(form: any) {
    let newPost = {
      productName: form.value.productName,
      brand: form.value.brand,
      productCode: form.value.productCode,
      prodPrice: form.value.prodPrice,
      id: this.service.getUser().id
    }

    this.service.addProduct(newPost).subscribe(
      () => {
        // Product added successfully
        this.router.navigate(['admin/products']);
      },
      (error) => {
        if (error.error.message === 'Product code already exists') {
          // Product code already exists
          this.errorMessage = 'Product code should be unique.';
        } else {
          this.errorMessage = 'An error occurred. Please try again.';
          console.log(error);
        }
      }
    );
  }
}
