import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user';
import { ServicesService } from '../services.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  msg: any;
  user = new User();
  
  constructor(private service: ServicesService, private router: Router) { }

  ngOnInit(): void {
  
  }

  registerUser(){
    this.service.registerUser(this.user).subscribe({
      next: (data) => {
        console.log("Success!");
        this.service.setUser(data);
        this.service.isLoggedIn = true;
        this.router.navigate(['login']);
      },
      error: (err) => {
        console.log("Error!");
        this.msg = "Please Check Your Email | Password";
      }
    });
  }
}
