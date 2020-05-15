import { Injectable } from '@angular/core';
import { Interceptor } from '../../_utilities/interceptor/interceptor';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from 'src/app/_models/user/user';
import { ApiService } from '../api/api.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private config = '/api/v1';
  private csrfToken: string;
  private currentUserSubject: BehaviorSubject<User>;
  private currentUser: Observable<User>;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Accept': 'text/html, application/xhtml+xml, */*'
    })
  };

  private profilePicSubject = new BehaviorSubject<any>(null);
  public profilePic: Observable<any>;

  constructor(
    private http: HttpClient,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')));
    this.currentUser = this.currentUserSubject.asObservable();
    this.profilePicSubject.next(null);
    this.profilePic = this.profilePicSubject.asObservable();
    if (this.currentUserValue) {
      this.getProfilePic()
        .subscribe(
          data => {
            this.profilePicSubject.next(data);
            this.profilePic = this.profilePicSubject.asObservable();
          }, error => {
            this.profilePicSubject.next(null);
            this.profilePic = this.profilePicSubject.asObservable();
          }
        );
    } else {
      this.profilePicSubject.next(null);
      this.profilePic = this.profilePicSubject.asObservable();
    }
    //this.csrfToken = this.getCSRF();

  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  public get profilePicValue(): any {
    return this.profilePicSubject.value;
  }

  public login(cred: any) {
    // if (!this.getCSRF()) {
    //   this.http.head<any>(`/api/v1/`)
    //     .subscribe(
    //       data => {
    //         this.csrfToken = this.getCSRF();
    //       }
    //     );
    // }
    this.http.post<User>(`${this.config}/login`, `username=${cred.username}&password=${cred.password}&_csrf=${this.getCSRF()}`, this.httpOptions)
      .subscribe(
        data => {
          this.currentUserSubject.next(data);
          localStorage.setItem('user', JSON.stringify(this.currentUserValue));
          this.router.navigate(['/']);
          this.toastr.success('Login successful');
          this.getProfilePic()
            .subscribe(
              data => {
                this.profilePicSubject.next(data);
              }
            )
        }, error => {
          this.toastr.error('Invalid credentials');
        }
      );
  }

  public logout() {
    // send logout request
    this.http.post<any>(`${this.config}/logout`, '', { headers: { 'X-XSRF-TOKEN': this.getCSRF() } })
      .subscribe(
        data => {
          this.toastr.success('Logout successful');
          this.router.navigate(['/login'])
        }, error => {
          //this.toastr.error('Could not logout');
          this.router.navigate(['/login']);
        }
      );
    this.currentUserSubject.next(null);
    localStorage.removeItem('user');
  }

  public uploadPhoto(file: File) {
    let formData = new FormData();
    formData.append('file', file, file.name);
    console.log(this.csrfToken);
    this.http.post<any>(`${this.config}/users/${this.currentUserValue.id}/profilepicture`, formData, { headers: { 'X-XSRF-TOKEN': this.getCSRF() } })
      .subscribe(
        data => {
          this.profilePicSubject.next(data);
          this.toastr.success('Profile picture updated');

          //location.reload();
        }, error => {
          if (error.status >= 400 && error.status < 500) {
            this.logout();
          } else {
            this.toastr.error('Could not update profile picture');
          }
        }
      );
  }

  public getProfilePic() {
    return this.http.get<any>(`${this.config}/users/${this.currentUserValue.id}/profilepicture`, { headers: { 'X-XSRF-TOKEN': this.getCSRF() } });
  }

  public getCSRF(): string {
    let cookies = document.cookie.split(';');
    let token = '';

    cookies.forEach(cookie => {
      let cookiePair = cookie.split('=');
      if (cookiePair[0] == 'XSRF-TOKEN') {
        token = cookiePair[1];
      }
    });

    return token;
  }

  public get csrfTokenValue() {
    return this.csrfToken;
  }

  private newCSRF() {
    console.log('THIS RAN');
    this.http.head<any>(`${this.config}`)
      .subscribe(
        data => {
          this.csrfToken = this.getCSRF();
          console.log(this.csrfToken);
          localStorage.setItem('csrf', this.csrfToken);
        }, error => {
          this.csrfToken = this.getCSRF();
          console.log(this.csrfToken);
          localStorage.setItem('csrf', this.csrfToken);
        }
      );
  }

}
