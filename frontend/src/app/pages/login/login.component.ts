import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  constructor(private formBuilder: FormBuilder, private authService: AuthService) {}

  form = this.formBuilder.nonNullable.group({
    username: ["", [Validators.required, Validators.pattern(/^\w{4,10}$/)]],
    password: ["", [Validators.required, Validators.minLength(6)]]
  });

  error= "";

  onSubmit() {
    const {username, password} = this.form.getRawValue();
    this.authService.login(username, password);

  }

  createAccount() {
    console.log("CREATE: ", this.form.value)
  }
}
