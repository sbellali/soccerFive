import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { FlowbiteInitService } from './services/flowbit-init.service';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements  OnInit{


  title = 'Soccer Five';
  constructor(private flowbiteInitService: FlowbiteInitService, private authService: AuthService){}

  ngOnInit(): void {
    this.flowbiteInitService.initFlowbite();
    
    this.authService.timerForToken();
  }

}
