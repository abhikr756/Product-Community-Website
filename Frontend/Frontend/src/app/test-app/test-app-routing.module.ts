import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddReviewsComponent } from './add-reviews/add-reviews.component';

import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { AddProductComponent } from './admin/products/add-product/add-product.component';
import { ProductsComponent } from './admin/products/products.component';
import { ReviewsComponent } from './admin/reviews/reviews.component';
import { UsersComponent } from './admin/users/users.component';


import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { RequestReviewComponent } from './request-review/request-review.component';

import { ShowReviewComponent } from './show-review/show-review.component';


import { NormalGuard } from './guards/normal.guard';
import { AdminGuard } from './guards/admin.guard';
import { SearchpageComponent } from './searchpage/searchpage.component';






const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'requestReview',
    component: RequestReviewComponent
  },
  {
    path: 'searchProduct',
    component: SearchpageComponent
  },
  {
    path:'admin',
    canActivate:[AdminGuard],
    component: DashboardComponent,
    children:[
      {
        path: 'users',
        component: UsersComponent
      },
      {
        path: 'products',
        component: ProductsComponent
      },
      {
        path: 'reviews',
        component: ReviewsComponent
      },
      {
        path: 'addProduct',
        component: AddProductComponent
      }
    ]
    
  },
  {
    path: 'addReview',
    canActivate: [NormalGuard],
    component: AddReviewsComponent
  },
  {
    path: 'showReview',
    canActivate: [NormalGuard],
    component: ShowReviewComponent
  },
  
  {
    path: '**',
    component: HomeComponent
  },


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TestAppRoutingModule { }
