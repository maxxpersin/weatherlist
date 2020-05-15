import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/_services/authentication/authentication.service';
import { Interceptor } from 'src/app/_utilities/interceptor/interceptor';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });

  formInvalid = false;

  constructor(
    private auth: AuthenticationService,
    private interceptor: Interceptor,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
  }

  ngSubmit() {
    if (this.loginForm.valid) {
      this.auth.login(this.loginForm.value)
      // if (error) {
      //   this.formInvalid = true;
      // } else {
      //   this.toastr.success('Login successful');
      // }
    } else {
      this.formInvalid = true;
    }
  }

}
