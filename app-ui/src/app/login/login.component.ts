import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form: FormGroup = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  }); 
  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router){}

  login(){
    let verify = this.authService.verify(this.form.value.username,this.form.value.password)
    .pipe()
    .subscribe(U => {
      console.log(U)
      this.authService.login(U);
      this.router.navigateByUrl('/dashboard');
    })
  }
}
