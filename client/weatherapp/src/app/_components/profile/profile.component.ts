import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/_services/authentication/authentication.service';
import { ApiService } from 'src/app/_services/api/api.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user = this.auth.currentUserValue;
  profilePic: any;

  constructor(
    private api: ApiService,
    private auth: AuthenticationService,
    private toastr: ToastrService
  ) {
    this.auth.profilePic.subscribe(
      data => {
        this.profilePic = data;
      }
    );
  }

  ngOnInit(): void {
  }

  fileSelected(event: any) {
    let fileToUpload: File = event.target.files[0];
    this.auth.uploadPhoto(fileToUpload);
  }

}
