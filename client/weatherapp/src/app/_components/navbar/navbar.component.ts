import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/_services/authentication/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  user = this.auth.currentUserValue;
  profilePic: any;

  constructor(
    private auth: AuthenticationService,
    private router: Router
  ) {
    this.auth.profilePic.subscribe(
      data => {
        this.profilePic = data;
      }
    );
  }

  public logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  validUser() {
    return this.auth.currentUserValue != null;
  }
}
