import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  form: FormGroup = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });
  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router) {}

  signup() {
    let user = this.form.value.username
    let pass = this.form.value.password
    let verify = this.authService.signup(user,pass)    
    .subscribe(bool => {
      if(!bool){
        alert("Username taken");
      }
      else{
        this.router.navigateByUrl('/');
      }
    })
  }
}
