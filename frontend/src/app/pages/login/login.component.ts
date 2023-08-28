import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router, RouterModule} from '@angular/router';
import { assetsPath } from 'src/environments';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  
  errorMessage = "";
  logoPath = assetsPath.logo;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {}

  form = this.formBuilder.nonNullable.group({
    username: ["", [Validators.required, Validators.pattern(/^\w{4,10}$/)]],
    password: ["", [Validators.required, Validators.minLength(6)]]
  });

  onSubmit() {
    const {username, password} = this.form.getRawValue();
    this.authService.login(username, password).subscribe({
          error: (err) => {
            this.errorMessage = err.error.message || "";
          }
      });
  }
}
