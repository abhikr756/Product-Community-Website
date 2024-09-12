import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServicesService } from '../../services.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: any;

  constructor(private service: ServicesService, private router: Router) { }

  ngOnInit(): void {
    this.showUsers();
  }

  showUsers(){
    this.service.showUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  userChange(user: any){
    const userData = {
      id: user.id,
      firstName: user.firstName,
      lastName: user.lastName,
      enabled: user.enabled,
      email: user.email,
      password: user.password
    };
    
    this.service.userSatus(userData).subscribe({
      next: (data) => {
        window.location.href = "admin/users";
      },
      error: (err) => {
        console.log(err);
      }
    });
    
  }
  
}
