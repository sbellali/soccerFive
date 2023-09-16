import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Session } from 'src/app/entities/session';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-session-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './session-list.component.html',
  styleUrls: ['./session-list.component.scss']
})
export class SessionListComponent implements OnInit {

  public sessions: Session[];
  public profilesImageLink = {};

  constructor(private sessionService: SessionService, private userService: UserService) {}

  ngOnInit(): void {
    this.sessionService.getSessionByDate("").subscribe({
      next: (sessions) => {
        this.sessions = sessions;
        this.fetchProfilesImage();
      }
    })
    
  }

  fetchProfilesImage() {
    this.sessions.forEach(session => {
        this.fetchProfileImage(session.organizer.id);
    });
  }
  fetchProfileImage(userId) {
    this.userService.fetchProfileImage(userId).subscribe({
      next: (res) => {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.profilesImageLink[userId] = e.target.result as string;
        };
        reader.readAsDataURL(res);
      }
    })
  }
}
