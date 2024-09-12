import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { User } from '../models/user';
import { ServicesService } from '../services.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  msg: string = ''; // Variable to store error message
  user: User = new User(); // User object to store login credentials
  
  constructor(private service: ServicesService, private router: Router) { }

  ngOnInit(): void {
    // Initialization code
  }

  /**
   * Function to log in the user.
   * It clears the local storage, generates a token for user login, and retrieves the current user details.
   * Based on the user's role, it navigates to the appropriate route.
   * If the user role is invalid, it logs out the user and displays an error message.
   */
  loginUser(): void {
    // Clear the local storage to ensure a clean login
    localStorage.clear();

    // Prepare login data object with email and password
    const loginData = {
      userName: this.user.email,
      password: this.user.password
    };
   
    // Generate token for user login by calling the `generateToken` service method
    this.service.generateToken(loginData).subscribe({
      next: (data: any) => {
        // Store the generated token in the service
        this.service.loginUserToken(data.token);

        // Retrieve the current user details by calling the `getCurrentUser` service method
        this.service.getCurrentUser().subscribe({
          next: (user: any) => {
            // Set the retrieved user details in the service
            this.service.setUser(user);

            // Get the user role from the service
            const userRole = this.service.getUserRole();

            // Navigate based on the user role
            if (userRole === 'ADMIN') {
              // Navigate to the admin home route
              this.router.navigate(['/admin/home']);
            } else if (userRole === 'NORMAL') {
              // Navigate to the normal user home route
              this.router.navigate(['/home']);
            } else {
              // Invalid user role, log out the user and display an error message
              this.service.logout();
              this.msg = 'Invalid user role';
            }
          },
          error: () => {
            console.log('Error');
            this.msg = 'Please check your email or password';
          }
        });
      },
      error: () => {
        console.log('Error');
        this.msg = 'Please check your email or password';
      }
    });
  }

  /**
   * Function to check if the user is logged in.
   * @returns boolean indicating the login status.
   */
  loggedIn(): boolean {
    // Check the login status using the `isLogIn` service method
    return this.service.isLogIn();
  }

  /**
   * Function to log out the user.
   * It logs out the user by calling the `logout` service method and navigates to the home route.
   */
  logout(): void {
    // Call the `logout` service method to log out the user
    this.service.logout();
    // Navigate to the home route
    this.router.navigate(['home']);
  }
}
