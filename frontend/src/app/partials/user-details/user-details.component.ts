import { Component} from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from 'src/app/entities/user';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-user-details-partial',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent {
  user: User;

  constructor(private userService: UserService, private router: Router, private authService: AuthService){}

  ngOnInit(): void {
    this.user = this.userService.getUserFromJwt()
  }

  logout(): void {
    this.authService.logout()
  }

  getUser(): User {
    return this.user;
  }

}
