import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { assetsPath, environment } from 'src/environments';
import { AuthService } from 'src/app/services/auth.service';
import { UserDetailsComponent } from '../user-details/user-details.component';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/entities/user';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, UserDetailsComponent],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  logoPath = assetsPath.logo;
  user: User;
  profileImageLink: string | null;
  constructor (private authService: AuthService, private userService: UserService) {}

  ngOnInit(): void {
    this.user = this.userService.getUserFromJwt();
    this.fetchProfileImage();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  logout(): void {
    this.authService.logout();
  }

  fetchProfileImage() {
    const userId = this.user.id;
    this.userService.fetchProfileImage(userId).subscribe({
      next: (res) => {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.profileImageLink = e.target.result as string;
        };
        reader.readAsDataURL(res);
      }
    })
  }
}
