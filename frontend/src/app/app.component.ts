import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { FlowbiteInitService } from './services/flowbit-init.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements  OnInit{


  title = 'Soccer Five';
  constructor(private flowbiteInitService: FlowbiteInitService){}

  ngOnInit(): void {
    this.flowbiteInitService.initFlowbite()
  }

}
