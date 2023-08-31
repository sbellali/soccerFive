import { Injectable } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import {filter} from 'rxjs';
import { initFlowbite } from 'flowbite';
@Injectable({
  providedIn: 'root'
})
export class FlowbiteInitService {

    constructor(private router: Router){}

    public initFlowbite() {
        this.router.events.pipe(
            filter((event) => event instanceof NavigationEnd)
        ).subscribe({
            next: () => {
                initFlowbite();
            },
            error: (error) => {
                console.log(error)
            }
        })
    }
}