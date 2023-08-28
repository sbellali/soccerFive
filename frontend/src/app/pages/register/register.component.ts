import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { assetsPath } from 'src/environments';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  errorMessage = "";
  logoPath = assetsPath.logo;
  
  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {}

  form = this.formBuilder.nonNullable.group({
    username: ["", [Validators.required, Validators.pattern(/^\w{4,10}$/)]],
    email: ["", [Validators.required, Validators.email]],
    password: ["", [Validators.required, Validators.minLength(6)]]
  });
  
  get registerFormControl() {
    return this.form.controls;
  }

  onSubmit() {
    const {username, email, password} = this.form.getRawValue();
    this.authService.register(username, email, password).subscribe({
      error: (err) => {
        this.errorMessage = err.error.message || "";
      }
    })
  }
}
