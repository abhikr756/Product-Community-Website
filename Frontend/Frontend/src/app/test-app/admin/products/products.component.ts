import { Component, OnInit } from '@angular/core';
import { ServicesService } from '../../services.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: any;

  constructor(private service: ServicesService) { }

  ngOnInit(): void {
    this.showProducts();
  }

  showProducts(): void {
    this.service.showProducts().subscribe({
      next: (data) => {
        this.products = data;
      },
      error: (err) => {
        console.log(err);
      }
    });
  }


  deleteProduct(productId: number): void {
    if (confirm('Are you sure you want to delete this product?')) {
      this.service.deleteProduct(productId).subscribe({
        next: () => {
          // Product successfully deleted, remove it from the products list
          this.products = this.products.filter((product: any) => product.productId !== productId);
        },
        error: (err) => {
          console.log('Error deleting product:', err);
        }
      });
    }
  }


  
  
}
