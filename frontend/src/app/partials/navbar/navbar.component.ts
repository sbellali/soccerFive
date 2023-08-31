import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { assetsPath } from 'src/environments';
import { AuthService } from 'src/app/services/auth.service';
import { UserDetailsComponent } from '../user-details/user-details.component';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, UserDetailsComponent],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  logoPath = assetsPath.logo
  constructor (private authService: AuthService) {}

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  logout(): void {
    this.authService.logout();
  }
}
