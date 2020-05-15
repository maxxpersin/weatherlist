import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/_services/api/api.service';
import { AuthenticationService } from 'src/app/_services/authentication/authentication.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Location } from '../../_models/location/location';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  user = this.auth.currentUserValue;
  showError = false;
  loading = false;
  public locationResponse = undefined;
  photoLink = '';

  locationAddForm = new FormGroup({
    latitude: new FormControl('', [Validators.required]),
    longitude: new FormControl('', [Validators.required])
  });

  constructor(
    private api: ApiService,
    private auth: AuthenticationService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.showError = false;
    this.loading = true;
    if (this.locationAddForm.valid) {
      this.api.searchLatLong(this.locationAddForm.value)
        .subscribe(
          data => {
            if (data.success == false) {
              this.toastr.error('No weather data for that location');
            } else {
              this.locationResponse = data.currentobservation;
              let photoName = this.locationResponse.Weatherimage;
              let links = data.data.iconLink;
              links.forEach(link => {
                if (link.split('/')[link.split('/').length - 1] == photoName) {
                  this.photoLink = link;
                }
              });
            }
            this.loading = false;
          }, error => {
            this.loading = false;
            if (error.status >= 500) {
              this.toastr.error('There was an issue retrieving that location');
            }
            if (error.status >= 400 && error.status < 500) {
              this.auth.logout();
            }
          }
        )
    } else {
      this.showError = true;
    }
  }

  saveLocation() {
    this.api.saveLocation({
      lat: this.locationResponse.latitude,
      lon: this.locationResponse.longitude,
      name: this.locationResponse.name,
      owner: this.auth.currentUserValue.id
    })
      .subscribe(
        data => {
          this.toastr.success('Location saved');
          //this.router.navigate(['/']);
        }, error => {
          this.toastr.error('Failure to save');
        }
      )
  }

}
