import { Component, OnInit } from '@angular/core';
import { ServicesService } from '../services.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-searchpage',
  templateUrl: './searchpage.component.html',
  styleUrls: ['./searchpage.component.css']
})
export class SearchpageComponent implements OnInit{
  
  searchQuery: any;
  products: any;
  stats: any;
  product:any;
  showAllClicked: boolean = false;  
  constructor(private service: ServicesService, private router: Router) { }
  ngOnInit(): void {
   
  }

  addReview(pId: any){
    
    localStorage.setItem("reviewProductId",pId);
    this.router.navigate(['addReview'], { queryParams: { productId: pId } });
  }
  showReview(pId: any){

    localStorage.setItem("reviewProductId",pId);
    this.router.navigate(['showReview']);
  }

  requestForreview(pId: any){
    localStorage.setItem("reviewProductId", pId);
    this.router.navigate(["requestReview"]);
  }

  loggedIn(){
    return this.service.isLogIn();
  }

  logout(){
    this.service.logout();
    this.router.navigate(['home']);
  }

  currentUser(){
    return this.service.getUser();
  }

  removeFilter(){
    localStorage.removeItem("products");
  }

  searchedProducts(){
   const p = localStorage.getItem("products");
   console.warn("Hello Yaar "+p);
   if(p!=null)
      return JSON.parse(p);
   return [];
  }

  OnInput(event: any) {
    this.searchQuery = event.target.value;
    console.warn("PEA "+this.searchQuery);
    
    this.service.searchProducts(this.searchQuery).subscribe(

      data => {this.products = data
        this.service.sProducts(this.products);
        window.scrollTo(0,8000);
      },
      err => {
        console.log(err)
      }
    )
    }  
 
    showAllProducts() {
      const p = localStorage.getItem("products");
      console.warn("Hello Yaaraaa "+p);
      if(p!=null)
         return JSON.parse(p);
      return [];
    }
  
   

}


